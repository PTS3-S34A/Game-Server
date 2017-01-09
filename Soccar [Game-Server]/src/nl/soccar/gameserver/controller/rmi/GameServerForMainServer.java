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
 * A GameServerForMainServer is an RMI-stub object used by the Main Server that
 * realizes the RMI-methods provided by the IGameServerForMainServer interface.
 *
 * @author PTS34A
 */
public final class GameServerForMainServer extends UnicastRemoteObject implements IGameServerForMainServer {

    /**
     * Initializes the GameServerForMainServer class.
     *
     * @throws RemoteException
     */
    public GameServerForMainServer() throws RemoteException {
        // Doesnt need any parameters for intializing.
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

    /**
     * Closes the RMI Stub.
     *
     * @throws IOException
     */
    public final void close() throws IOException {
        UnicastRemoteObject.unexportObject(this, true);
    }

}
