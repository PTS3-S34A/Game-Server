package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.ChangeGameStatusMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.CHANGE_GAME_STATUS_MESSAGE_ID, handler = ChangeGameStatusMessageHandler.class)
public final class ChangeGameStatusMessage extends Message {

    @Override
    public int getId() {
        return MessageConstants.CHANGE_GAME_STATUS_MESSAGE_ID;
    }

}
