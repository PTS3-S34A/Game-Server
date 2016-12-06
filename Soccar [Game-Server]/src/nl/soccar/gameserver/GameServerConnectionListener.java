package nl.soccar.gameserver;

import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.library.Session;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.connection.ConnectionListener;

/**
 *
 * @author PTS34A
 */
public final class GameServerConnectionListener implements ConnectionListener {

    @Override
    public void onConnected(Connection connection) {
    }

    @Override
    public void onDisconnected(Connection connection) {
        Player player = connection.getPlayer();
        if (player == null) {
            return;
        }

        Session session = player.getCurrentSession();
        if (session == null) {
            return;
        }

        SessionController.getInstance().leaveSession(player, session);
    }

}
