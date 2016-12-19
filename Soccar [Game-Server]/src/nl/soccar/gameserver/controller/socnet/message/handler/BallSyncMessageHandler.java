package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.BallSyncMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * @author PTS34A
 */
public final class BallSyncMessageHandler extends MessageHandler<BallSyncMessage> {

    @Override
    protected void handle(Connection connection, BallSyncMessage message) throws Exception {
        throw new UnsupportedOperationException("handling not supported for Server");
    }

    @Override
    protected void encode(Connection connection, BallSyncMessage message, ByteBuf buf) throws Exception {
        buf.writeFloat(message.getX());
        buf.writeFloat(message.getY());

        buf.writeFloat(message.getLinearVelocityX());
        buf.writeFloat(message.getLinearVelocityY());
        buf.writeFloat(message.getAngularVelocity());
    }

    @Override
    protected BallSyncMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("decoding not supported for Server");
    }

}
