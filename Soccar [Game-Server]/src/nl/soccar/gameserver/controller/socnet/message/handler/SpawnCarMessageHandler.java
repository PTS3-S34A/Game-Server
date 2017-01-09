package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.SpawnCarMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * Handler for the SpawnCarMessage class.
 * 
 * @author PTS34A
 */
public final class SpawnCarMessageHandler extends MessageHandler<SpawnCarMessage> {

    @Override
    protected void handle(Connection connection, SpawnCarMessage message) throws Exception {
        throw new UnsupportedOperationException("handling not supported for Server");
    }

    @Override
    protected void encode(Connection connection, SpawnCarMessage message, ByteBuf buf) throws Exception {
        buf.writeByte(message.getPlayerId());
        buf.writeFloat(message.getX());
        buf.writeFloat(message.getY());
        buf.writeFloat(message.getAngle());
    }

    @Override
    protected SpawnCarMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("decoding not supported for Server");
    }

}
