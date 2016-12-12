package nl.soccar.gameserver;

import nl.soccar.gameserver.message.JoinSessionMessage;
import nl.soccar.gameserver.message.MovePlayerMessage;
import nl.soccar.gameserver.rmi.GameServerController;
import nl.soccar.library.GameSettings;
import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.*;
import nl.soccar.socnet.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author PTS34A
 */
public class SessionController {

    private static final Logger LOGGER = Logger.getLogger(SessionController.class.getSimpleName());

    private static SessionController instance;

    private final GameServerController controller;
    private final Node server;

    private final Map<String, SessionWrapper> sessions = new HashMap<>();

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

        SessionWrapper wrapper = new SessionWrapper(server, this, session);

        synchronized (sessions) {
            sessions.put(name, wrapper);
            controller.sessionCreated(name, hostName, !password.isEmpty(), capacity);
        }

        LOGGER.log(Level.INFO, "Session {0} created.", name);
        return true;
    }

    public void destroySession(Session session) {
        if (session == null) {
            return;
        }

        String name = session.getRoom().getName();
        synchronized (sessions) {
            SessionWrapper wrapper = sessions.remove(name);
            if (wrapper == null) {
                return;
            }

            // TODO remove all players, or something...
            controller.sessionDestroyed(name);
        }

        LOGGER.log(Level.INFO, "Session {0} destroyed.", name);
    }

    public JoinSessionMessage.Status joinSession(Player player, String name, String password) {
        synchronized (sessions) {
            SessionWrapper wrapper = sessions.get(name);
            if (wrapper == null) {
                return JoinSessionMessage.Status.SESSION_NON_EXISTENT;
            }


            JoinSessionMessage.Status status = wrapper.join(player, password);
            if (status == JoinSessionMessage.Status.SUCCESS) {
                controller.increaseOccupancy(name);
            }

            return status;
        }
    }

    public void leaveSession(Player player, Session session) {
        if (session == null) {
            return;
        }

        Room room = session.getRoom();
        String name = room.getName();
        synchronized (sessions) {
            SessionWrapper wrapper = sessions.get(name);
            if (wrapper == null) {
                return;
            }

            wrapper.leaveSession(player);
        }

        LOGGER.log(Level.INFO, "Player {0} left session.", new String[]{player.getUsername()});


        if (room.getOccupancy() <= 0) {
            destroySession(session);
            return;
        }

        controller.changeHost(name, room.getHost().getUsername());
        controller.decreaseOccupancy(name);
    }

    public void startGame(Session session) {
        if (session == null) {
            return;
        }

        String name = session.getRoom().getName();
        synchronized (sessions) {
            SessionWrapper wrapper = sessions.get(name);
            if (wrapper == null) {
                return;
            }

            wrapper.startGame();
        }

        LOGGER.log(Level.INFO, "Game from {0} started.", name);
    }

    public void movePlayer(Player player, SteerAction steerAction, HandbrakeAction handbrakeAction, ThrottleAction throttleAction) {
        Room room = player.getCurrentSession().getRoom();

        MovePlayerMessage m = new MovePlayerMessage(player.getUsername(), steerAction, handbrakeAction, throttleAction);
        room.getAllPlayers().stream().filter(p -> !p.equals(player)).map(server::getConnectionFromPlayer).forEach(c -> c.send(m));
    }

}
