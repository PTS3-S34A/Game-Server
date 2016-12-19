package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.StartGameMessage;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.session.GameWrapper;
import nl.soccar.gameserver.model.session.SessionWrapper;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * @author PTS34A
 */
public final class StartGameMessageHandler extends MessageHandler<StartGameMessage> {

    @Override
    protected void handle(Connection connection, StartGameMessage message) throws Exception {
        GameServer server = GameServer.getInstance();
        PlayerWrapper player = server.getPlayer(connection);
        if (player == null) {
            return;
        }

        SessionWrapper session = player.getCurrentSession();
        if (session == null) {
            return;
        }

        GameWrapper game = session.getGame();
        game.start();
    }

    @Override
    protected void encode(Connection connection, StartGameMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding is not supported on Server.");
    }

    @Override
    protected StartGameMessage decode(Connection connection, ByteBuf buf) throws Exception {
        return new StartGameMessage();
    }

}
