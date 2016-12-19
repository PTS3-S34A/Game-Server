package nl.soccar.gameserver.model;

import nl.soccar.gameserver.controller.rmi.GameServerRmiController;
import nl.soccar.gameserver.controller.socnet.GameServerSocnetController;
import nl.soccar.gameserver.model.session.SessionController;
import nl.soccar.gameserver.model.session.SessionWrapper;
import nl.soccar.library.Session;
import nl.soccar.socnet.connection.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lesley
 */
public final class GameServer {

    private static final GameServer instance = new GameServer();

    private final Map<Connection, PlayerWrapper> players = new HashMap<>();
    private final SessionController sessionController = new SessionController();
    private GameServerRmiController rmiController;
    private GameServerSocnetController socnetController;

    private GameServer() {
    }

    public static GameServer getInstance() {
        return instance;
    }

    public void registerPlayer(PlayerWrapper player) {
        registerPlayer(player.getConnection(), player);
    }

    public void registerPlayer(Connection connection, PlayerWrapper player) {
        players.put(connection, player);
    }

    public void deregisterPlayer(PlayerWrapper player) {
        deregisterPlayer(player.getConnection());
    }

    public void deregisterPlayer(Connection connection) {
        PlayerWrapper player = players.remove(connection);
        if (player == null) {
            return;
        }

        Session session = player.unwrap().getCurrentSession();
        if (session == null) {
            return;
        }

        SessionWrapper wrapper = sessionController.getSession(session.getRoom().getName());
        wrapper.getRoom().leave(player);
    }

    public PlayerWrapper getPlayer(Connection connection) {
        return players.get(connection);
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public GameServerRmiController getRmiControler() {
        return rmiController;
    }

    public void setRmiController(GameServerRmiController rmiController) {
        this.rmiController = rmiController;
    }

    public GameServerSocnetController getSocnetController() {
        return socnetController;
    }

    public void setSocnetController(GameServerSocnetController socnetController) {
        this.socnetController = socnetController;
    }

}
