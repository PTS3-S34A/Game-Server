package nl.soccar.gameserver.message;

import nl.soccar.gameserver.message.handler.StartGameMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.START_GAME_MESSAGE_ID, handler = StartGameMessageHandler.class)
public final class StartGameMessage extends Message {
    
    @Override
    public int getId() {
        return MessageConstants.START_GAME_MESSAGE_ID;
    }
    
}
