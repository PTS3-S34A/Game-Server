package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.MovePlayerMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * @author PTS34A
 */
public final class MovePlayerMessageHandler extends MessageHandler<MovePlayerMessage> {

    @Override
    protected void handle(Connection connection, MovePlayerMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling is not supported for the Server.");
    }

    @Override
    protected void encode(Connection connection, MovePlayerMessage message, ByteBuf buf) throws Exception {
        buf.writeByte(message.getPlayerId());
        buf.writeByte(message.getSteerAction().getId());
        buf.writeByte(message.getHandbrakAction().getId());
        buf.writeByte(message.getThrottleAction().getId());
    }

    @Override
    protected MovePlayerMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding is not supported for the Server.");
    }

}
