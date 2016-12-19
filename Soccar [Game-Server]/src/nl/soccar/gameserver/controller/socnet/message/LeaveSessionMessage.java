package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.LeaveSessionMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_LEAVE_SESSION_MESSAGE_ID, handler = LeaveSessionMessageHandler.class)
public class LeaveSessionMessage extends Message {

    @Override
    public int getId() {
        return MessageConstants.PLAYER_LEAVE_SESSION_MESSAGE_ID;
    }

}
