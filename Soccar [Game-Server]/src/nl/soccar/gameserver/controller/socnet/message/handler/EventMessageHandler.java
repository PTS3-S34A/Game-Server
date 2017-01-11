package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.EventMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * Handler for the EventMessage class.
 *
 * @author PTS34A
 */
public final class EventMessageHandler extends MessageHandler<EventMessage> {

    @Override
    protected void handle(Connection connection, EventMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling not supported for Server.");
    }

    @Override
    protected void encode(Connection connection, EventMessage message, ByteBuf buf) throws Exception {
        buf.writeByte(message.getEventType().getId());
        buf.writeByte(message.getPlayerId());
    }

    @Override
    protected EventMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding not supported for Server.");
    }

}
