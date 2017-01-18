package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.GameStatusMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * Handler for the GameStatusMessage class.
 *
 * @author PTS34A
 */
public final class GameStatusMessageHandler extends MessageHandler<GameStatusMessage> {

    @Override
    protected void handle(Connection connection, GameStatusMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling not supported for Server.");
    }

    @Override
    protected void encode(Connection connection, GameStatusMessage message, ByteBuf buf) throws Exception {
        buf.writeByte(message.getGameStatus().ordinal());
    }

    @Override
    protected GameStatusMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding not supported for Server.");
    }

}
