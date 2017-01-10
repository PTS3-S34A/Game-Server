package nl.soccar.gameserver.model.session;

import nl.soccar.gameserver.model.GameServer;
import nl.soccar.library.GameSettings;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.library.enumeration.MapType;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the Sessions on the Game Server.
 *
 * @author PTS34A
 */
public final class SessionController {

    private static final Logger LOGGER = Logger.getLogger(SessionController.class.getSimpleName());

    private final Map<String, SessionWrapper> sessions = new HashMap<>();

    /**
     * Creates a Session.
     *
     * @param roomName The name of the Room, not null.
     * @param password The password of the Room, not null.
     * @param hostName The host of the Room, not null.
     * @param capacity The capacity of the Room, not null.
     * @param duration The duration of the Game, not null.
     * @param mapType The map type of the Map, not null.
     * @param ballType The ball type of the Room, not null.
     *
     * @return boolean True if the session is created, false if it isn't
     * created.
     */
    public boolean createSession(String roomName, String password, String hostName, int capacity, Duration duration, MapType mapType, BallType ballType) {
        Session session = new Session(roomName, password);
        session.getRoom().setCapacity(capacity);

        GameSettings gameSettings = session.getGame().getGameSettings();
        gameSettings.setDuration(duration);
        gameSettings.setMapType(mapType);
        gameSettings.setBallType(ballType);

        SessionWrapper wrapper = new SessionWrapper(session);

        synchronized (sessions) {
            sessions.put(roomName, wrapper);
        }

        GameServer.getInstance().getRmiController().sessionCreated(roomName, hostName, !password.isEmpty(), capacity);
        LOGGER.log(Level.INFO, "Session {0} created.", new String[]{roomName});
        return true;
    }

    /**
     * Destroys the Session.
     *
     * @param roomName The given Room, not null.
     */
    public void destroySession(String roomName) {
        synchronized (sessions) {
            SessionWrapper session = sessions.remove(roomName);
            if (session == null) {
                return;
            }
        }

        GameServer.getInstance().getRmiController().sessionDestroyed(roomName);
        LOGGER.log(Level.INFO, "Session {0} destroyed.", new String[]{roomName});
    }

    /**
     * Gets the Session.
     *
     * @param roomName The given Room, not null.
     *
     * @return SessionWrapper The session.
     */
    public SessionWrapper getSession(String roomName) {
        synchronized (sessions) {
            return sessions.get(roomName);
        }
    }

}
