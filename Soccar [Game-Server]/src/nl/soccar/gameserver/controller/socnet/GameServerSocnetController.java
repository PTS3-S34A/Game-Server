package nl.soccar.gameserver.controller.socnet;

import nl.soccar.gameserver.controller.socnet.message.*;
import nl.soccar.socnet.Server;
import nl.soccar.socnet.message.MessageRegistry;

import java.io.IOException;

/**
 * Controller for the socket connections from the GameServer.
 *
 * @author PTS34A
 */
public final class GameServerSocnetController {

    private final int port;
    private Server server;

    /**
     * Initializes the gamServerSocnetController class.
     *
     * @param port The port for the GameServer socket connection, not null.
     */
    public GameServerSocnetController(int port) {
        this.port = port;
    }

    /**
     * Initializes the server Node on the GameServer.
     */
    public void initialize() {
        server = new Server();
        server.addListener(new GameServerConnectionListener());

        MessageRegistry registry = server.getMessageRegistry();
        registry.register(RegisterPlayerMessage.class);

        registry.register(JoinSessionMessage.class);
        registry.register(LeaveSessionMessage.class);
        registry.register(ChangeHostMessage.class);

        registry.register(PlayerJoinedSessionMessage.class);
        registry.register(PlayerLeftSessionMessage.class);

        registry.register(ChatMessage.class);
        registry.register(SwitchTeamMessage.class);

        registry.register(ChangeGameStatusMessage.class);
        registry.register(PlayerChangedGameStatusMessage.class);

        registry.register(MovePlayerMessage.class);
        registry.register(PlayerMovedMessage.class);

        registry.register(SpawnCarMessage.class);
        registry.register(SpawnObstacleMessage.class);
        registry.register(SpawnBallMessage.class);

        registry.register(ChangePlayerStatusMessage.class);
        registry.register(PlayerSyncMessage.class);
        registry.register(BallSyncMessage.class);
        registry.register(EventMessage.class);
    }

    /**
     * Binds the port to the Server Node.
     * 
     * @throws IOException 
     */
    public void bind() throws IOException {
        server.bind(port);
    }

    /**
     * Closes the Server Node.
     */
    public void close() {
        if (server != null) {
            server.close();
        }
    }

}
