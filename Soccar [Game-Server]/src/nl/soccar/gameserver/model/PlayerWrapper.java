package nl.soccar.gameserver.model;

import nl.soccar.gameserver.model.session.SessionWrapper;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.socnet.connection.Connection;

/**
 * Wrapper for the player class.
 * 
 * @author PTS34A
 */
public final class PlayerWrapper {

    private final Connection connection;
    private final Player player;

    /**
     * Initializes the PlayerWrapper class.
     * 
     * @param connection The connection of the player, not null.
     * @param player The player, not null.
     */
    public PlayerWrapper(Connection connection, Player player) {
        this.connection = connection;
        this.player = player;
    }

    /**
     * Gets the current session of the player.
     * 
     * @return SessionWrapper The current session of the player.
     */
    public SessionWrapper getCurrentSession() {
        Session session = player.getCurrentSession();
        if (session == null) {
            return null;
        }

        return GameServer.getInstance().getSessionController().getSession(session.getRoom().getName());
    }

    /**
     * Gets the id of the player.
     * 
     * @return int The id of the player.
     */
    public int getPlayerId() {
        return player.getPlayerId();
    }

    /**
     * Gets the username of the player.
     * 
     * @return String The username of the player.
     */
    public String getUsername() {
        return player.getUsername();
    }

    /**
     * Gets the type of the car.
     * 
     * @return CarType The type of the car.
     */
    public CarType getCarType() {
        return player.getCarType();
    }

    /**
     * Gets the privilege of the player.
     * 
     * @return Privilege The privilege of the player.
     */
    public Privilege getPrivilege() {
        return player.getPrivilege();
    }

    /**
     * Gets the connection of the player.
     * 
     * @return Connection The connection of the player.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Unwraps the player object.
     * 
     * @return Player The player.
     */
    public Player unwrap() {
        return player;
    }

}
