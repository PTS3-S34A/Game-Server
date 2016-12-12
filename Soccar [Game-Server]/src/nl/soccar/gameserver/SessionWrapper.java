package nl.soccar.gameserver;

import nl.soccar.gameserver.message.JoinSessionMessage;
import nl.soccar.gameserver.message.PlayerJoinedSessionMessage;
import nl.soccar.gameserver.message.PlayerLeftSessionMessage;
import nl.soccar.gameserver.message.PlayerStartedGameMessage;
import nl.soccar.library.*;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.Node;
import nl.soccar.socnet.connection.Connection;

public final class SessionWrapper {

    private final Node server;
    private final SessionController controller;
    private final Session session;

    public SessionWrapper(Node server, SessionController controller, Session session) {
        this.server = server;
        this.controller = controller;
        this.session = session;
    }

    public JoinSessionMessage.Status join(Player player, String password) {
        Room room = session.getRoom();

        int occupation = room.getOccupancy();
        if (occupation >= room.getCapacity()) {
            return JoinSessionMessage.Status.CAPACITY_OVERFLOW;
        }

        if (!room.check(password)) {
            return JoinSessionMessage.Status.INVALID_PASSWORD;
        }

        if (room.getAllPlayers().stream().anyMatch(player::equals)) {
            return JoinSessionMessage.Status.USERNAME_EXISTS;
        }

        Team teamBlue = room.getTeamBlue();
        Team teamRed = room.getTeamRed();

        Team team = occupation % 2 == 0 ? teamBlue : teamRed;
        team.join(player);

        player.setCurrentSession(session);

        sendJoinSessionResponse(player);
        sendJoinedSessionNotification(player, team.getTeamColour());

        return JoinSessionMessage.Status.SUCCESS;
    }

    private void sendJoinSessionResponse(Player player) {
        Game game = session.getGame();
        Room room = session.getRoom();

        // Send session data to connecting player
        Connection connection = server.getConnectionFromPlayer(player);
        connection.send(new JoinSessionMessage(JoinSessionMessage.Status.SUCCESS, room.getName(), room.getCapacity(), game.getGameSettings()));

        // Send the connecting player all stuff
        room.getTeamBlue().getPlayers().stream().filter(p -> !p.equals(player)).map(p -> new PlayerJoinedSessionMessage(p, TeamColour.BLUE)).forEach(connection::send);
        room.getTeamRed().getPlayers().stream().filter(p -> !p.equals(player)).map(p -> new PlayerJoinedSessionMessage(p, TeamColour.RED)).forEach(connection::send);
    }

    private void sendJoinedSessionNotification(Player player, TeamColour team) {
        Room room = session.getRoom();

        // Send notification to all players that the connecting player joined
        PlayerJoinedSessionMessage m = new PlayerJoinedSessionMessage(player, team);
        room.getAllPlayers().stream().map(server::getConnectionFromPlayer).forEach(c -> c.send(m));
    }

    public void leaveSession(Player player) {
        Room room = session.getRoom();
        Team team = getTeamFromPlayer(player);

        team.leave(player);
        player.setCurrentSession(null);

        PlayerLeftSessionMessage m = new PlayerLeftSessionMessage(player.getUsername(), team.getTeamColour());
        room.getAllPlayers().stream().map(server::getConnectionFromPlayer).forEach(c -> c.send(m));

        if (room.getCapacity() <= 0) {
            controller.destroySession(session);
        }
    }

    public void startGame() {
        Room room = session.getRoom();

        PlayerStartedGameMessage m = new PlayerStartedGameMessage();
        room.getAllPlayers().stream().map(server::getConnectionFromPlayer).forEach(c -> c.send(m));

        Game game = session.getGame();
        game.start();
    }

    private Team getTeamFromPlayer(Player player) {
        Room room = session.getRoom();
        Team teamBlue = room.getTeamBlue();
        Team teamRed = room.getTeamRed();

        if (teamBlue.getPlayers().contains(player)) {
            return teamBlue;
        }

        if (teamRed.getPlayers().contains(player)) {
            return teamRed;
        }

        return null;
    }

    public Session getSession() {
        return session;
    }

}
