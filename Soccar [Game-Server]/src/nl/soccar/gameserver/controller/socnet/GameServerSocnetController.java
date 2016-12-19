package nl.soccar.gameserver.controller.socnet;

import nl.soccar.gameserver.controller.socnet.message.*;
import nl.soccar.socnet.Server;
import nl.soccar.socnet.message.MessageRegistry;

import java.io.IOException;

/**
 * @author PTS34A
 */
public final class GameServerSocnetController {

    private final int port;
    private Server server;

    public GameServerSocnetController(int port) {
        this.port = port;
    }

    public void initialize() {
        server = new Server();
        server.addListener(new GameServerConnectionListener());

        MessageRegistry registry = server.getMessageRegistry();
        registry.register(RegisterPlayerMessage.class);

        registry.register(JoinSessionMessage.class);
        registry.register(LeaveSessionMessage.class);

        registry.register(PlayerJoinedSessionMessage.class);
        registry.register(PlayerLeftSessionMessage.class);

        registry.register(ChatMessage.class);
        registry.register(SwitchTeamMessage.class);

        registry.register(StartGameMessage.class);
        registry.register(PlayerStartedGameMessage.class);

        registry.register(MovePlayerMessage.class);
        registry.register(PlayerMovedMessage.class);

        registry.register(PlayerSyncMessage.class);
        registry.register(BallSyncMessage.class);
    }

    public void bind() throws IOException {
        server.bind(port);
    }

    public void close() {
        if (server != null) {
            server.close();
        }
    }

}
