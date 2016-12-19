package nl.soccar.gameserver.model.session;

import nl.soccar.gameserver.controller.rmi.GameServerRmiController;
import nl.soccar.gameserver.controller.socnet.message.*;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.command.Command;
import nl.soccar.gameserver.model.command.CommandDispatcher;
import nl.soccar.gameserver.util.RoomUtilities;
import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.library.Team;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.connection.Connection;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Lesley
 */
public final class RoomWrapper {

    private static final Logger LOGGER = Logger.getLogger(RoomWrapper.class.getSimpleName());
    private static final Random RANDOM = new Random();

    private final List<PlayerWrapper> players = new ArrayList<>();
    private final Map<PlayerWrapper, Team> teams = new HashMap<>();

    private final List<String> mutedNames = new ArrayList<>();

    private final SessionWrapper session;
    private final Room room;

    public RoomWrapper(SessionWrapper session, Room room) {
        this.session = session;
        this.room = room;
    }

    public void join(PlayerWrapper player, String password) {
        if (room.getOccupancy() + 1 > room.getCapacity()) {
            player.getConnection().send(new JoinSessionMessage(JoinSessionMessage.Status.CAPACITY_OVERFLOW));
            return;
        }

        if (!room.check(password)) {
            player.getConnection().send(new JoinSessionMessage(JoinSessionMessage.Status.INVALID_PASSWORD));
            return;
        }

        Player unwrapped = player.unwrap();
        synchronized (players) {
            if (players.stream()
                    .map(PlayerWrapper::unwrap)
                    .anyMatch(p -> p.equals(unwrapped))) {
                player.getConnection().send(new JoinSessionMessage(JoinSessionMessage.Status.USERNAME_EXISTS));
                return;
            }

            players.add(player);

            unwrapped.setPlayerId(RoomUtilities.getNextPlayerId(this));
            unwrapped.setCurrentSession(session.unwrap());
        }

        joinRandomTeam(player);
        sendRoomInformation(player);

        String roomName = room.getName();
        LOGGER.log(Level.INFO, "Player {0} joined session {1}.", new String[]{player.getUsername(), roomName});

        GameServerRmiController rmiController = GameServer.getInstance().getRmiControler();
        rmiController.increaseOccupancy(roomName);
        rmiController.changeHost(roomName, room.getHost().getUsername());
    }

    public void leave(PlayerWrapper player) {
        synchronized (players) {
            PlayerLeftSessionMessage message = new PlayerLeftSessionMessage(player.getPlayerId());

            players.remove(player);
            players.stream()
                    .map(PlayerWrapper::getConnection)
                    .forEach(c -> c.send(message));
        }

        Player unwrapped = player.unwrap();
        unwrapped.setCurrentSession(null);
        unwrapped.setPlayerId(0);

        synchronized (teams) {
            Team team = teams.remove(player);
            if (team != null) {
                team.leave(unwrapped);
            }
        }

        String roomName = room.getName();
        LOGGER.log(Level.INFO, "Player {0} left session {1}.", new String[]{player.getUsername(), roomName});

        if (room.getOccupancy() <= 0) {
            GameServer.getInstance().getSessionController().destroySession(room.getName());
            return;
        }

        GameServerRmiController rmiController = GameServer.getInstance().getRmiControler();
        rmiController.decreaseOccupancy(roomName);
        rmiController.changeHost(roomName, room.getHost().getUsername());
    }

    public void switchTeam(PlayerWrapper player) {
        synchronized (teams) {
            Team oldTeam = teams.remove(player);
            Team newTeam = oldTeam.getTeamColour() == TeamColour.BLUE ? room.getTeamRed() : room.getTeamBlue();

            teams.put(player, newTeam);

            Player unwrapped = player.unwrap();
            oldTeam.leave(unwrapped);
            newTeam.join(unwrapped);

            SwitchTeamMessage message = new SwitchTeamMessage(player.getPlayerId(), newTeam.getTeamColour());
            players.stream()
                    .map(PlayerWrapper::getConnection)
                    .forEach(c -> c.send(message));
        }
    }

    public void sendChatMessage(PlayerWrapper player, String message) {
        if (message.startsWith("/")) {
            String[] components = message.substring(1).split(" ");
            String name = components[0];

            String[] arguments = new String[components.length - 1];
            System.arraycopy(components, 1, arguments, 0, arguments.length);

            Command command = new Command(name, arguments);
            CommandDispatcher.dispatch(player, command);
            return;
        }

        if (mutedNames.contains(player.getUsername().toLowerCase())) {
            player.getConnection().send(new ChatMessage(-1, Privilege.ADMINISTRATOR, "You have been muted and, therefor, cannot talk."));
            return;
        }

        ChatMessage m = new ChatMessage(player.getPlayerId(), player.getPrivilege(), message);
        synchronized (players) {
            players.stream()
                    .map(PlayerWrapper::getConnection)
                    .forEach(c -> c.send(m));
        }
    }

    public void mutePlayer(PlayerWrapper issuer, String username) {
        String lower = username.toLowerCase();
        if (mutedNames.contains(lower)) {
            return;
        }

        mutedNames.add(lower);
        issuer.getConnection().send(new ChatMessage(-1, Privilege.ADMINISTRATOR, String.format("You have muted %s.", username)));

        synchronized (players) {
            Optional<PlayerWrapper> optional = players.stream().filter(p -> p.getUsername().equalsIgnoreCase(username)).findFirst();
            if (!optional.isPresent()) {
                return;
            }

            optional.get().getConnection().send(new ChatMessage(-1, Privilege.ADMINISTRATOR, "You have been muted."));
        }
    }

    public void unmutePlayer(PlayerWrapper issuer, String username) {
        mutedNames.remove(username.toLowerCase());
        issuer.getConnection().send(new ChatMessage(-1, Privilege.ADMINISTRATOR, String.format("You have unmuted %s.", username)));

        synchronized (players) {
            Optional<PlayerWrapper> optional = players.stream().filter(p -> p.getUsername().equalsIgnoreCase(username)).findFirst();
            if (!optional.isPresent()) {
                return;
            }

            optional.get().getConnection().send(new ChatMessage(-1, Privilege.ADMINISTRATOR, "You have been unmuted."));
        }
    }

    private void joinRandomTeam(PlayerWrapper player) {
        Team blue = room.getTeamBlue();
        int blueSize = blue.getSize();

        Team red = room.getTeamRed();
        int redSize = red.getSize();

        boolean random = RANDOM.nextBoolean();
        if (blueSize < redSize || blueSize == redSize && random) {
            blue.join(player.unwrap());
            teams.put(player, blue);
        } else {
            red.join(player.unwrap());
            teams.put(player, red);
        }
    }

    private void sendRoomInformation(PlayerWrapper player) {
        Connection connection = player.getConnection();

        // Step 1, send the joining player all information about the room.
        connection.send(new JoinSessionMessage(JoinSessionMessage.Status.SUCCESS, room.getName(), room.getCapacity(), session.getGame().getGameSettings()));

        synchronized (player) {
            synchronized (teams) {
                // Step 2, send the joining player information about all OTHER players that joined the room.
                players.stream()
                        .filter(p -> p.getPlayerId() != player.getPlayerId())
                        .map(p -> new PlayerJoinedSessionMessage(p.getPlayerId(), p.getUsername(), p.getPrivilege(), p.getCarType(), teams.get(p).getTeamColour()))
                        .forEach(connection::send);

                // Step 3, send ALL players information about the joining player.
                Team joinedTeam = teams.get(player);
                PlayerJoinedSessionMessage joinedMessage = new PlayerJoinedSessionMessage(player.getPlayerId(), player.getUsername(), player.getPrivilege(), player.getCarType(), joinedTeam.getTeamColour());

                players.stream()
                        .map(PlayerWrapper::getConnection)
                        .forEach(c -> c.send(joinedMessage));
            }
        }
    }

    public PlayerWrapper getHost() {
        synchronized (players) {
            Optional<PlayerWrapper> optional = players.stream().filter(p -> p.getUsername().equals(room.getHost().getUsername())).findFirst();
            if (!optional.isPresent()) {
                return null;
            }

            return optional.get();
        }
    }

    public List<PlayerWrapper> getPlayers() {
        synchronized (players) {
            return Collections.unmodifiableList(players);
        }
    }

    public Team getTeamBlue() {
        return room.getTeamBlue();
    }

    public Team getTeamRed() {
        return room.getTeamRed();
    }
}
