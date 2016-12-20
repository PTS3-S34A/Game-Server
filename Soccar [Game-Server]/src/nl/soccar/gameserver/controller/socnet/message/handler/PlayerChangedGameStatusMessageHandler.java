package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.PlayerChangedGameStatusMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * @author PTS34A
 */
public final class PlayerChangedGameStatusMessageHandler extends MessageHandler<PlayerChangedGameStatusMessage> {

    @Override
    protected void handle(Connection connection, PlayerChangedGameStatusMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling is not supported for the Server.");
    }

    @Override
    protected void encode(Connection connection, PlayerChangedGameStatusMessage message, ByteBuf buf) throws Exception {
        buf.writeByte(message.getStatus().ordinal());
    }

    @Override
    protected PlayerChangedGameStatusMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding is not supported for the Server.");
    }

}
