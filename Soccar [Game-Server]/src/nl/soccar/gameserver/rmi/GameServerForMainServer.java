package nl.soccar.gameserver.rmi;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.library.enumeration.MapType;
import nl.soccar.rmi.interfaces.IGameServerForMainServer;

/**
 *
 * @author PTS34A
 */
public class GameServerForMainServer extends UnicastRemoteObject implements IGameServerForMainServer {

    private static final Logger LOGGER = Logger.getLogger(GameServerForMainServer.class.getSimpleName());

    private final GameServerController controller;

    public GameServerForMainServer(GameServerController controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public boolean createSession(String name, String password, int capacity, Duration duration, MapType mapType, BallType ballType) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Unexports this RMI-stub object.
     */
    public final void close() {
        try {
            UnicastRemoteObject.unexportObject(this, true);
            LOGGER.log(Level.INFO, "Unregistered {0} binding.", getClass().getSimpleName());
        } catch (NoSuchObjectException e) {
            LOGGER.log(Level.SEVERE, "Server could not be unexported.", e);
        }
    }

}
