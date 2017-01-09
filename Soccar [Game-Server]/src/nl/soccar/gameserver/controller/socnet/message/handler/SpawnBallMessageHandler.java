package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.SpawnBallMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * Handler for the SpawnBallMessage class.
 * 
 * @author PTS34A
 */
public final class SpawnBallMessageHandler extends MessageHandler<SpawnBallMessage> {

    @Override
    protected void handle(Connection connection, SpawnBallMessage message) throws Exception {
        throw new UnsupportedOperationException("handling not supported for Server");
    }

    @Override
    protected void encode(Connection connection, SpawnBallMessage message, ByteBuf buf) throws Exception {
        buf.writeFloat(message.getX());
        buf.writeFloat(message.getY());
        buf.writeFloat(message.getAngle());
        buf.writeByte(message.getBallType().getId());
    }

    @Override
    protected SpawnBallMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("decoding not supported for Server");
    }

}
