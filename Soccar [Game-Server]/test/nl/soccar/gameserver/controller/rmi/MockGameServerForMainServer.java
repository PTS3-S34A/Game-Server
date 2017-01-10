package nl.soccar.gameserver.controller.rmi;

import java.rmi.RemoteException;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.library.enumeration.MapType;
import nl.soccar.rmi.interfaces.IGameServerForMainServer;

/**
 * Mock for the IGameServerForMainServer class.
 * 
 * @author PTS34A
 */
public class MockGameServerForMainServer implements IGameServerForMainServer{
    
    @Override
    public boolean createSession(String name, String password, String hostName, int capacity, Duration duration, MapType mapType, BallType ballType) throws RemoteException {
        return true;
    }

    @Override
    public long getAvailableMemory() throws RemoteException {
        return 1;
    }

    @Override
    public int ping(int value) throws RemoteException {
        return 1;
    }
    
}
