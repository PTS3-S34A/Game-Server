package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.SpawnObstacleMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * Handler for the SpawnObstacleMessage class.
 * 
 * @author PTS34A
 */
public final class SpawnObstacleMessageHandler extends MessageHandler<SpawnObstacleMessage> {

    @Override
    protected void handle(Connection connection, SpawnObstacleMessage message) throws Exception {
        throw new UnsupportedOperationException("handling not supported for Server");
    }

    @Override
    protected void encode(Connection connection, SpawnObstacleMessage message, ByteBuf buf) throws Exception {
        buf.writeFloat(message.getX());
        buf.writeFloat(message.getY());
        buf.writeFloat(message.getAngle());
        buf.writeFloat(message.getWidth());
        buf.writeFloat(message.getHeight());
        buf.writeByte(message.getObstacleType().getId());
    }

    @Override
    protected SpawnObstacleMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("decoding not supported for Server");
    }

}
