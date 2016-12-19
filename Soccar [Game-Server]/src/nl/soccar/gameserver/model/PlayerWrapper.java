package nl.soccar.gameserver.model;

import nl.soccar.gameserver.model.session.SessionWrapper;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.socnet.connection.Connection;

/**
 * @author PTS34A
 */
public final class PlayerWrapper {

    private final Connection connection;
    private final Player player;

    public PlayerWrapper(Connection connection, Player player) {
        this.connection = connection;
        this.player = player;
    }

    public SessionWrapper getCurrentSession() {
        Session session = player.getCurrentSession();
        if (session == null) {
            return null;
        }

        return GameServer.getInstance().getSessionController().getSession(session.getRoom().getName());
    }

    public int getPlayerId() {
        return player.getPlayerId();
    }

    public String getUsername() {
        return player.getUsername();
    }

    public CarType getCarType() {
        return player.getCarType();
    }

    public Privilege getPrivilege() {
        return player.getPrivilege();
    }

    public Connection getConnection() {
        return connection;
    }

    public Player unwrap() {
        return player;
    }

}
