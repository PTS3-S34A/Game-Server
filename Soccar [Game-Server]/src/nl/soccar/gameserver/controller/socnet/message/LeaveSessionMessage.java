package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.LeaveSessionMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for leaving a Session.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.LEAVE_SESSION_MESSAGE_ID, handler = LeaveSessionMessageHandler.class)
public class LeaveSessionMessage extends Message {

    @Override
    public int getId() {
        return MessageConstants.LEAVE_SESSION_MESSAGE_ID;
    }

}
