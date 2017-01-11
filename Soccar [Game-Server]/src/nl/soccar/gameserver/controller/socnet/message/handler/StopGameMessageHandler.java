package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.StopGameMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * Handler for StopGameMessage.
 * 
 * @author PTS34A
 */
public class StopGameMessageHandler extends MessageHandler<StopGameMessage> {

    @Override
    protected void handle(Connection connection, StopGameMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling is not supported for Server."); 
    }

    @Override
    protected void encode(Connection connection, StopGameMessage message, ByteBuf buf) throws Exception {
        // Doesnt need to encode anything.
    }

    @Override
    protected StopGameMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding is not supported for Server.");
    }
    
}
