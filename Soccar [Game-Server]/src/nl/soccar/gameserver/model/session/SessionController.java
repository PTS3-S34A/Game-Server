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
 * @author PTS34A
 */
public final class SessionController {

    private static final Logger LOGGER = Logger.getLogger(SessionController.class.getSimpleName());

    private final Map<String, SessionWrapper> sessions = new HashMap<>();

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

        GameServer.getInstance().getRmiControler().sessionCreated(roomName, hostName, !password.isEmpty(), capacity);
        LOGGER.log(Level.INFO, "Session {0} created.", new String[]{roomName});
        return true;
    }

    public void destroySession(String roomName) {
        synchronized (sessions) {
            SessionWrapper session = sessions.remove(roomName);
            if (session == null) {
                return;
            }

            session.destroy();
        }

        GameServer.getInstance().getRmiControler().sessionDestroyed(roomName);
        LOGGER.log(Level.INFO, "Session {0} destroyed.", new String[]{roomName});
    }

    public SessionWrapper getSession(String roomName) {
        synchronized (sessions) {
            return sessions.get(roomName);
        }
    }

}
