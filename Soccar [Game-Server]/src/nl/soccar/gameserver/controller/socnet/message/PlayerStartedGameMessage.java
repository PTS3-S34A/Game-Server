package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.PlayerStartedGameMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_STARTED_GAME_MESSAGE_ID, handler = PlayerStartedGameMessageHandler.class)
public final class PlayerStartedGameMessage extends Message {

    @Override
    public int getId() {
        return MessageConstants.PLAYER_STARTED_GAME_MESSAGE_ID;
    }

}
