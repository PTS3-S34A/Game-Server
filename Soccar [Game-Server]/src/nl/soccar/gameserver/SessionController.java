package nl.soccar.gameserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.soccar.gameserver.message.JoinSessionMessage;
import nl.soccar.gameserver.message.PlayerJoinedSessionMessage;
import nl.soccar.gameserver.message.PlayerLeftSessionMessage;
import nl.soccar.gameserver.rmi.GameServerController;
import nl.soccar.library.*;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.library.enumeration.MapType;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.Node;
import nl.soccar.socnet.connection.Connection;

/**
 *
 * @author PTS34A
 */
public class SessionController {

    private static final Logger LOGGER = Logger.getLogger(SessionController.class.getSimpleName());

    private static SessionController instance;

    private final GameServerController controller;
    private final Node server;

    private final List<Session> sessions = new ArrayList<>();

    private SessionController(GameServerController controller, Node server) {
        this.controller = controller;
        this.server = server;
    }

    public static SessionController getInstance() {
        return instance;
    }

    public static void setInstance(GameServerController controller, Node server) {
        instance = new SessionController(controller, server);
    }

    public boolean createSession(String name, String password, String hostName, int capacity, Duration duration, MapType mapType, BallType ballType) {
        Session session = new Session(name, password);
        session.getRoom().setCapacity(capacity);        

        GameSettings gameSettings = session.getGame().getGameSettings();
        gameSettings.setDuration(duration);
        gameSettings.setMapType(mapType);
        gameSettings.setBallType(ballType);

        synchronized (sessions) {
            sessions.add(session);
            controller.sessionCreated(name, hostName, !password.isEmpty(), capacity);
        }

        LOGGER.log(Level.INFO, "Session {0} created.", name);
        return true;
    }

    public void destroySession(String name) {
        synchronized (sessions) {
            Optional<Session> optional = sessions.stream().filter(s -> s.getRoom().getName().equals(name)).findAny();
            if (optional.isPresent()) {
                sessions.remove(optional.get());
            }
        }

        LOGGER.log(Level.INFO, "Session {0} destroyed.", name);
    }

    public JoinSessionMessage.Status joinSession(Player player, String roomName, String password) {
        synchronized (sessions) {
            Optional<Session> optional = sessions.stream().filter(s -> s.getRoom().getName().equals(roomName)).findFirst();
            if (!optional.isPresent()) {
                return JoinSessionMessage.Status.SESSION_NON_EXISTENT;
            }

            Session session = optional.get();
            Room room = session.getRoom();

            int occupation = room.getOccupancy();
            if (occupation >= room.getCapacity()) {
                return JoinSessionMessage.Status.CAPACITY_OVERFLOW;
            }

            if (!room.check(password)) {
                return JoinSessionMessage.Status.INVALID_PASSWORD;
            }

            if (room.getAllPlayers().stream().map(Player::getUsername).anyMatch(n -> player.getUsername().equals(n))) {
                return JoinSessionMessage.Status.USERNAME_EXISTS;
            }

            Team teamBlue = room.getTeamBlue();
            Team teamRed = room.getTeamRed();

            Team team = occupation % 2 == 0 ? teamBlue : teamRed;
            team.join(player);

            player.setCurrentSession(session);

            sendJoinSessionResponse(player, room, session.getGame());
            sendJoinedSessionNotification(player, team.getTeamColour(), room);

            return JoinSessionMessage.Status.SUCCESS;
        }
    }

    private void sendJoinSessionResponse(Player joinedPlayer, Room room, Game game) {
        // Send session data to connecting player
        Connection connection = server.getConnectionFromPlayer(joinedPlayer);
        connection.send(new JoinSessionMessage(JoinSessionMessage.Status.SUCCESS, room.getName(), room.getCapacity(), game.getGameSettings()));

        // Send the connecting player all stuff
        room.getTeamBlue().getPlayers().stream().filter(p -> p != joinedPlayer).map(p -> new PlayerJoinedSessionMessage(p, TeamColour.BLUE)).forEach(connection::send);
        room.getTeamRed().getPlayers().stream().filter(p -> p != joinedPlayer).map(p -> new PlayerJoinedSessionMessage(p, TeamColour.RED)).forEach(connection::send);
    }

    private void sendJoinedSessionNotification(Player joinedPlayer, TeamColour joinedTeam, Room room) {
        // Send notification to all players that the connecting player joined
        PlayerJoinedSessionMessage m = new PlayerJoinedSessionMessage(joinedPlayer, joinedTeam);
        room.getAllPlayers().stream().map(server::getConnectionFromPlayer).forEach(c -> c.send(m));
    }

    public boolean leaveSession(Player player, TeamColour colour, Session session) {
        Room room = session.getRoom();
        
        Team team = colour == TeamColour.BLUE ? room.getTeamBlue() : room.getTeamRed();
        team.leave(player);

        player.setCurrentSession(null);

        room.getAllPlayers().stream().map(server::getConnectionFromPlayer).forEach(c -> System.out.println(c));
        
        PlayerLeftSessionMessage m = new PlayerLeftSessionMessage(player.getUsername(), colour);
        room.getAllPlayers().stream().map(server::getConnectionFromPlayer).forEach(c -> c.send(m));
        LOGGER.log(Level.INFO, "Player {0} left the Session.", player.getUsername());
        return true;
    }
    
    public void leaveSession(Player player, Session session) {
        Room room = player.getCurrentSession().getRoom();
        
        Team teamBlue = room.getTeamBlue();
        
        TeamColour colour = teamBlue.getPlayers().stream().filter(p -> p.getUsername().equals(player.getUsername())).count() > 0 ? TeamColour.BLUE : TeamColour.RED;
        leaveSession(player, colour, session);
    }

    public List<Session> getAllSessions() {
        LOGGER.log(Level.INFO, "All sessions retrieved");

        synchronized (sessions) {
            return Collections.unmodifiableList(sessions);
        }
    }

}
