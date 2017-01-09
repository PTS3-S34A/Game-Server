package nl.soccar.gameserver.controller.rmi;

import nl.soccar.library.SessionData;
import nl.soccar.rmi.RmiConstants;
import nl.soccar.rmi.interfaces.IMainServerForGameServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class that is responsible for handling RMI network communication
 * with the remote Main server.
 * 
 * @author PTS34A
 */
public final class GameServerRmiController {

    private static final Logger LOGGER = Logger.getLogger(GameServerRmiController.class.getSimpleName());
    private final String host;
    private final int port;
    private Registry registry;
    private IMainServerForGameServer mainServerForGameServer;
    private GameServerForMainServer gameServerForMainServer;

    /**
     * Initializes the GameServerRmiController class.
     * 
     * @param host
     * @param port 
     */
    public GameServerRmiController(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void initialize() throws IOException {
        registry = LocateRegistry.getRegistry(host, port);
    }

    public void bind() throws NotBoundException, RemoteException {
        mainServerForGameServer = (IMainServerForGameServer) registry.lookup(RmiConstants.BINDING_NAME_MAIN_SERVER_FOR_GAME_SERVER);
    }

    public void register() throws IOException {
        gameServerForMainServer = new GameServerForMainServer(this);
        mainServerForGameServer.register(gameServerForMainServer);
    }

    public void sessionCreated(String roomName, String hostName, boolean hasPassword, int capacity) {
        try {
            String ipAddress = InetAddress.getLocalHost().getHostAddress();

            SessionData session = new SessionData(ipAddress, roomName, hostName, hasPassword);
            session.setCapacity(capacity);

            mainServerForGameServer.sessionCreated(gameServerForMainServer, session);
        } catch (UnknownHostException | RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while reporting a created session.", e);
        }
    }

    public void sessionDestroyed(String roomName) {
        try {
            mainServerForGameServer.sessionDestroyed(gameServerForMainServer, roomName);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while reporting a destroyed session.", e);
        }
    }

    public void increaseOccupancy(String roomName) {
        try {
            mainServerForGameServer.increaseSessionOccupancy(gameServerForMainServer, roomName);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while reporting increased occupancy for a session.", e);
        }
    }

    public void decreaseOccupancy(String roomName) {
        try {
            mainServerForGameServer.decreaseSessionOccupancy(gameServerForMainServer, roomName);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while reporting decreased occupancy for a session.", e);
        }
    }

    public void changeHost(String roomName, String host) {
        try {
            mainServerForGameServer.hostChanged(gameServerForMainServer, roomName, host);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while reporting a changed host.", e);
        }
    }

    public void close() {
        if (gameServerForMainServer != null) {
            try {
                gameServerForMainServer.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "An error occurred while closing the GameServer stub.", e);
            }
        }
    }

}
