package nl.soccar.gameserver.controller.socnet;

import nl.soccar.gameserver.model.GameServer;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.connection.ConnectionListener;

/**
 * Connection listener for the GameServer.
 * 
 * @author PTS34A
 */
public final class GameServerConnectionListener implements ConnectionListener {

    @Override
    public void onConnected(Connection connection) {
        // Won't do anything, we registerPlayer when we get a registerPlayer message.
    }

    @Override
    public void onDisconnected(Connection connection) {
        GameServer.getInstance().deregisterPlayer(connection);
    }

}
