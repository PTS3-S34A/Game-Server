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
 * Controller for the Gameserver.
 * 
 * @author PTS34A
 */
public final class GameServer {

    private static final GameServer INSTANCE = new GameServer();

    private final Map<Connection, PlayerWrapper> players = new HashMap<>();
    private final SessionController sessionController = new SessionController();
    private GameServerRmiController rmiController;
    private GameServerSocnetController socnetController;

    private GameServer() {
    }

    /**
     * Gets the Instance of the GameServer class.
     * 
     * @return GameServer The GameServer class.
     */
    public static GameServer getInstance() {
        return INSTANCE;
    }

    /**
     * Registers the player.
     * 
     * @param player The given player, not null.
     */
    public void registerPlayer(PlayerWrapper player) {
        registerPlayer(player.getConnection(), player);
    }

    /**
     * Registers the player
     * 
     * @param connection The given connection, not null.
     * @param player The given player, not null.
     */
    public void registerPlayer(Connection connection, PlayerWrapper player) {
        players.put(connection, player);
    }

    /**
     * Deregisters the player.
     * 
     * @param player The given player, not null.
     */
    public void deregisterPlayer(PlayerWrapper player) {
        deregisterPlayer(player.getConnection());
    }

    /**
     * Deregisters the player.
     * 
     * @param connection The given Connection, not null.
     */
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

    /**
     * Gets the Player from the given Connection.
     * 
     * @param connection The given Connection, not null.
     * @return PlayerWrapper The player.
     */
    public PlayerWrapper getPlayer(Connection connection) {
        return players.get(connection);
    }

    /**
     * Gets the SessionController.
     * 
     * @return SessionController The SessionController.
     */
    public SessionController getSessionController() {
        return sessionController;
    }

    /**
     * Gets the RMI Controller.
     * 
     * @return GameServerRmiController The RMI Controller.
     */
    public GameServerRmiController getRmiController() {
        return rmiController;
    }

    /**
     * Sets the RMI Controller
     * 
     * @param rmiController The given RMI Controller.
     */
    public void setRmiController(GameServerRmiController rmiController) {
        this.rmiController = rmiController;
    }

    /**
     * Gets the Socnet Controller.
     * 
     * @return GameServerSocnetController The socnet Controller.
     */
    public GameServerSocnetController getSocnetController() {
        return socnetController;
    }

    /**
     * Sets the Socnet Controller.
     * 
     * @param socnetController The given Socnet Controller.
     */
    public void setSocnetController(GameServerSocnetController socnetController) {
        this.socnetController = socnetController;
    }

}
