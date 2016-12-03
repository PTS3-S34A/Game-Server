package nl.soccar.gameserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.soccar.gameserver.rmi.GameServerController;
import nl.soccar.library.GameSettings;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.library.enumeration.MapType;

/**
 *
 * @author PTS34A
 */
public class SessionController {

    private static final Logger LOGGER = Logger.getLogger(SessionController.class.getSimpleName());

    private static SessionController instance;

    private final GameServerController controller;
    private final List<Session> sessions;

    private SessionController(GameServerController controller) {
        this.controller = controller;
        sessions = new ArrayList<>();
    }

    public static SessionController getInstance() {
        return instance;
    }

    public static void setInstance(GameServerController controller) {
        instance = new SessionController(controller);
    }

    public boolean createSession(String name, String password, int capacity, Duration duration, MapType mapType, BallType ballType) {
        Session session = new Session(name, password);
        session.getRoom().setCapacity(capacity);

        GameSettings gameSettings = session.getGame().getGameSettings();
        gameSettings.setDuration(duration);
        gameSettings.setMapType(mapType);
        gameSettings.setBallType(ballType);

        synchronized (sessions) {
            sessions.add(session);
            controller.sessionCreated(name, "host", !password.isEmpty(), capacity);
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

    public List<Session> getAllSessions() {
        LOGGER.log(Level.INFO, "All sessions retrieved");

        synchronized (sessions) {
            return Collections.unmodifiableList(sessions);
        }
    }

}
