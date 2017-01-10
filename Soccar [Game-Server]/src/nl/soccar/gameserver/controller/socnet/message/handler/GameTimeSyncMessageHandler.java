package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.GameTimeSyncMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * Handler for the GameTimeSyncMessage.
 * 
 * @author PTS34A
 */
public class GameTimeSyncMessageHandler extends MessageHandler<GameTimeSyncMessage> {

    @Override
    protected void handle(Connection connection, GameTimeSyncMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling is not supported for Server.");
    }

    @Override
    protected void encode(Connection connection, GameTimeSyncMessage message, ByteBuf buf) throws Exception {
        buf.writeInt(message.getGameTime());
        buf.writeLong(message.getCurrentTime());
    }

    @Override
    protected GameTimeSyncMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding is not supported for Server."); 
    }
    
}
