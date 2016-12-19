package nl.soccar.gameserver;

import nl.soccar.gameserver.controller.rmi.GameServerRmiController;
import nl.soccar.gameserver.controller.socnet.GameServerSocnetController;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.rmi.RmiConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author PTS34A
 */
public final class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getSimpleName());
    private static final String LOCATION_PROPERTIES = "mainserver.properties";

    private GameServerRmiController rmiController;
    private GameServerSocnetController socnetController;

    public static void main(String[] args) {
        Main main = new Main();

        try {
            main.initialize();
            main.start();
        } catch (Exception e) {
            main.close();

            LOGGER.log(Level.SEVERE, "An error occurred while initialising the GameServer.", e);
        }
    }

    private void initialize() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(LOCATION_PROPERTIES)) {
            properties.load(input);
        }

        GameServer server = GameServer.getInstance();

        rmiController = new GameServerRmiController(properties.getProperty("mainserver"), RmiConstants.PORT_NUMBER_GAME_SERVER);
        rmiController.initialize();
        server.setRmiController(rmiController);

        socnetController = new GameServerSocnetController(1046);
        socnetController.initialize();
        server.setSocnetController(socnetController);
    }

    private void start() throws IOException, NotBoundException {
        rmiController.bind();
        rmiController.register();

        socnetController.bind();
    }

    private void close() {
        if (rmiController != null) {
            rmiController.close();
        }

        if (socnetController != null) {
            socnetController.close();
        }
    }

}
