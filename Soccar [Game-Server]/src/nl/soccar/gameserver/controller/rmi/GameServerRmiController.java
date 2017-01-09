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
import nl.soccar.library.enumeration.Privilege;

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
     * @param host The given host, not null.
     * @param port The given port, not null.
     */
    public GameServerRmiController(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Intializes the GameServerRmiController.
     *
     * @throws IOException The IOException.
     */
    public void initialize() throws IOException {
        registry = LocateRegistry.getRegistry(host, port);
    }

    /**
     * Binds the GameServer RMI Controller.
     *
     * @throws NotBoundException
     * @throws RemoteException
     */
    public void bind() throws NotBoundException, RemoteException {
        mainServerForGameServer = (IMainServerForGameServer) registry.lookup(RmiConstants.BINDING_NAME_MAIN_SERVER_FOR_GAME_SERVER);
    }

    /**
     * Registers the GameServer RMI Controller.
     *
     * @throws IOException
     */
    public void register() throws IOException {
        gameServerForMainServer = new GameServerForMainServer(this);
        mainServerForGameServer.register(gameServerForMainServer);
    }

    /**
     * Creates a session.
     *
     * @param roomName The given Room Name, not null.
     * @param hostName The given Host Name, not null.
     * @param hasPassword The given hassPassword boolean, not null.
     * @param capacity The given capacity of the room, not null.
     */
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

    /**
     * Destroys the Session.
     *
     * @param roomName The given room name.
     */
    public void sessionDestroyed(String roomName) {
        try {
            mainServerForGameServer.sessionDestroyed(gameServerForMainServer, roomName);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while reporting a destroyed session.", e);
        }
    }

    /**
     * Increases the Occupancy of the given room.
     *
     * @param roomName The given room name, not null.
     */
    public void increaseOccupancy(String roomName) {
        try {
            mainServerForGameServer.increaseSessionOccupancy(gameServerForMainServer, roomName);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while reporting increased occupancy for a session.", e);
        }
    }

    /**
     * Decreases the Occupancy of the given room.
     *
     * @param roomName The given room name, not null.
     */
    public void decreaseOccupancy(String roomName) {
        try {
            mainServerForGameServer.decreaseSessionOccupancy(gameServerForMainServer, roomName);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while reporting decreased occupancy for a session.", e);
        }
    }

    /**
     * Changes the host of the given room.
     *
     * @param roomName The given room, not null.
     * @param host The given host, not null.
     */
    public void changeHost(String roomName, String host) {
        try {
            mainServerForGameServer.hostChanged(gameServerForMainServer, roomName, host);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while reporting a changed host.", e);
        }
    }

    public void addGoals(String username, int goals) {
        try {
            mainServerForGameServer.addGoals(username, goals);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while adding goals.", e);
        }
    }

    public void addAssists(String username, int assists) {
        try {
            mainServerForGameServer.addAssists(username, assists);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while adding assists.", e);
        }
    }

    public void incrementGamesWon(String username) {
        try {
            mainServerForGameServer.incrementGamesWon(username);
            incrementGamesPlayed(username);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while incrementing games won.", e);
        }
    }

    public void incrementGamesLost(String username) {
        try {
            mainServerForGameServer.incrementGamesLost(username);
            incrementGamesPlayed(username);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while incrementing games lost.", e);
        }
    }

    public void incrementGamesPlayed(String username) {
        try {
            mainServerForGameServer.incrementGamesPlayed(username);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while incrementing games played.", e);
        }
    }

    public Privilege getPrivilege(String username) {
        Privilege privilege = Privilege.NORMAL;

        try {
            privilege = mainServerForGameServer.getPrivilege(username);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while retrieving the privilege of a user.", e);
        }

        return privilege;
    }

    /**
     * Closes the RMI controller.
     */
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
