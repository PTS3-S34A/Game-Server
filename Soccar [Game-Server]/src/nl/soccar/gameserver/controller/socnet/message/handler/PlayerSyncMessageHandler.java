package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.PlayerSyncMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * @author PTS34A
 */
public final class PlayerSyncMessageHandler extends MessageHandler<PlayerSyncMessage> {

    @Override
    protected void handle(Connection connection, PlayerSyncMessage message) throws Exception {
        throw new UnsupportedOperationException("handling not supported for Server");
    }

    @Override
    protected void encode(Connection connection, PlayerSyncMessage message, ByteBuf buf) throws Exception {
        buf.writeByte(message.getPlayerId());

        buf.writeFloat(message.getX());
        buf.writeFloat(message.getY());
        buf.writeFloat(message.getAngle());

        buf.writeFloat(message.getLinearVelocityX());
        buf.writeFloat(message.getLinearVelocityY());
        buf.writeFloat(message.getAngularVelocity());
    }

    @Override
    protected PlayerSyncMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("decoding not supported for Server");
    }

}
