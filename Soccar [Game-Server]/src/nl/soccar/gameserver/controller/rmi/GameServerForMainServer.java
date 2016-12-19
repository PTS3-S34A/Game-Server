package nl.soccar.gameserver.controller.rmi;

import nl.soccar.gameserver.model.GameServer;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.library.enumeration.MapType;
import nl.soccar.rmi.interfaces.IGameServerForMainServer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import nl.soccar.rmi.RmiConstants;

/**
 * @author PTS34A
 */
public final class GameServerForMainServer extends UnicastRemoteObject implements IGameServerForMainServer {

    private final GameServerRmiController controller;

    public GameServerForMainServer(GameServerRmiController controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public boolean createSession(String name, String password, String hostName, int capacity, Duration duration, MapType mapType, BallType ballType) throws RemoteException {
        return GameServer.getInstance().getSessionController().createSession(name, password, hostName, capacity, duration, mapType, ballType);
    }

    @Override
    public long getAvailableMemory() throws RemoteException {
        return Runtime.getRuntime().freeMemory();
    }

    @Override
    public int ping(int value) throws RemoteException {
        return value * RmiConstants.PING_CALCULATION_FACTOR;
    }

    public final void close() throws IOException {
        UnicastRemoteObject.unexportObject(this, true);
    }

}
