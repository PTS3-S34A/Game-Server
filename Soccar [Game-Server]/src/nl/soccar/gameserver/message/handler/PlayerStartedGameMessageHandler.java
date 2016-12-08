package nl.soccar.gameserver.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.message.PlayerStartedGameMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 *
 * @author PTS34A
 */
public final class PlayerStartedGameMessageHandler extends MessageHandler<PlayerStartedGameMessage> {

    @Override
    protected void handle(Connection connection, PlayerStartedGameMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling is not supported for the Server.");
    }

    @Override
    protected void encode(Connection connection, PlayerStartedGameMessage message, ByteBuf buf) throws Exception {
        // There is no data to encode for this message.
    }

    @Override
    protected PlayerStartedGameMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding is not supported for the Server.");
    }

}
