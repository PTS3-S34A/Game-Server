package nl.soccar.gameserver.rmi;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.soccar.gameserver.GameServerConnectionListener;
import nl.soccar.gameserver.SessionController;
import nl.soccar.gameserver.message.*;
import nl.soccar.library.SessionData;
import nl.soccar.rmi.RmiConstants;
import nl.soccar.rmi.interfaces.IMainServerForGameServer;
import nl.soccar.socnet.Server;
import nl.soccar.socnet.message.MessageRegistry;

/**
 * @author PTS34A
 */
public class GameServerController {

    private static final Logger LOGGER = Logger.getLogger(GameServerController.class.getSimpleName());

    private static final String LOCATION_PROPERTIES = "mainserver.properties";

    private IMainServerForGameServer mainServerForGameServer;
    private GameServerForMainServer gameServerForMainServer;

    private Server server;

    public GameServerController() {
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream(LOCATION_PROPERTIES)) {
            props.load(input);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "An error occurred while loading the mainserver properties file.", e);
        }

        try {
            Registry r = LocateRegistry.getRegistry(props.getProperty("mainserver"), RmiConstants.PORT_NUMBER_GAME_SERVER);
            mainServerForGameServer = (IMainServerForGameServer) r.lookup(RmiConstants.BINDING_NAME_MAIN_SERVER_FOR_GAME_SERVER);

            LOGGER.info("Connection with the Main server is established.");

            gameServerForMainServer = new GameServerForMainServer(this);
            mainServerForGameServer.register(gameServerForMainServer);

            LOGGER.info("Registered this Game server on the Main server.");

            server = new Server();
            server.addListener(new GameServerConnectionListener());

            MessageRegistry registry = server.getMessageRegistry();
            registry.register(RegisterPlayerMessage.class);
            registry.register(JoinSessionMessage.class);
            registry.register(PlayerJoinedSessionMessage.class);
            registry.register(PlayerLeftSessionMessage.class);
            registry.register(PlayerLeaveSessionMessage.class);
            registry.register(PlayerStartedGameMessage.class);
            registry.register(StartGameMessage.class);
            registry.register(MovePlayerMessage.class);
            registry.register(PlayerMovedMessage.class);
            registry.register(ChatMessage.class);

            SessionController.setInstance(this, server);

            server.bind(1046);
        } catch (IOException | NotBoundException e) {
            LOGGER.log(Level.WARNING, "An error occurred while connecting to the Main server through RMI.", e);
        }
    }

    /**
     * Unexports the RMI-stub.
     */
    public void close() {
        gameServerForMainServer.close();
    }

    public void deregister() {
        try {
            mainServerForGameServer.deregister(gameServerForMainServer);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while deregistering this game server on the main server.", e);
        }
    }

    public void sessionCreated(String roomName, String hostName, boolean hasPassword, int capacity) {
        try {
            String ipAddress = InetAddress.getLocalHost().getHostAddress();

            SessionData session = new SessionData(ipAddress, roomName, hostName, hasPassword);
            session.setCapacity(capacity);

            mainServerForGameServer.sessionCreated(gameServerForMainServer, session);
        } catch (UnknownHostException | RemoteException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while reporting a created session.", e);
        }
    }

    public void sessionDestroyed(String roomName) {
        try {
            mainServerForGameServer.sessionDestroyed(gameServerForMainServer, roomName);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while reporting a destroyed session.", e);
        }
    }

    public void increaseOccupancy(String roomName) {
        try {
            mainServerForGameServer.increaseSessionOccupancy(gameServerForMainServer, roomName);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while reporting increased occupancy for a session.", e);
        }
    }

    public void decreaseOccupancy(String roomName) {
        try {
            mainServerForGameServer.decreaseSessionOccupancy(gameServerForMainServer, roomName);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while reporting decreased occupancy for a session.", e);
        }
    }

    public void changeHost(String roomName, String host) {
        try {
            mainServerForGameServer.hostChanged(gameServerForMainServer, roomName, host);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while reporting a changed host.", e);
        }
    }

}
