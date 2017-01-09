package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.ChangeHostMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * Handler for the ChangeHostMessage class.
 *
 * @author PTS34A
 */
public final class ChangeHostMessageHandler extends MessageHandler<ChangeHostMessage> {

    @Override
    protected void handle(Connection connection, ChangeHostMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling not supported for Server.");
    }

    @Override
    protected void encode(Connection connection, ChangeHostMessage message, ByteBuf buf) throws Exception {
        buf.writeByte(message.getPlayerId());
    }

    @Override
    protected ChangeHostMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding not supported for Server.");
    }

}
