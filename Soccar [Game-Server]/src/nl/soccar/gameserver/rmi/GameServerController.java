package nl.soccar.gameserver.rmi;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.soccar.gameserver.SessionController;
import nl.soccar.library.SessionData;
import nl.soccar.rmi.RmiConstants;
import nl.soccar.rmi.interfaces.IMainServerForGameServer;

/**
 *
 * @author PTS34A
 */
public class GameServerController {
    
    private static final Logger LOGGER = Logger.getLogger(GameServerController.class.getSimpleName());
    
    private static final String LOCATION_PROPERTIES = "mainserver.properties";
    
    private IMainServerForGameServer mainServerForGameServer;
    private GameServerForMainServer gameServerForMainServer;
    
    public GameServerController() {
        SessionController.setInstance(this);
        Properties props = new Properties();
        
        try (FileInputStream input = new FileInputStream(LOCATION_PROPERTIES)) {
            props.load(input);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "An error occurred while loading the mainserver properties file.", e);
        }
        
        try {
            Registry r = LocateRegistry.getRegistry(props.getProperty("mainserver"), RmiConstants.PORT_NUMBER_GAME_SERVER);
            mainServerForGameServer = (IMainServerForGameServer) r.lookup(RmiConstants.BINDING_NAME_MAIN_SERVER_FOR_GAME_SERVER);
            
            gameServerForMainServer = new GameServerForMainServer(this);
            mainServerForGameServer.register(gameServerForMainServer);
            
        } catch (RemoteException | NotBoundException e) {
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
        SessionData session = new SessionData("IPADDRESS", roomName, hostName, hasPassword);
        session.setCapacity(capacity);
        // TODO set occupation
        
        try {
            mainServerForGameServer.sessionCreated(gameServerForMainServer, session);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while reporting a created session.", e);
        }
    }
    
}
