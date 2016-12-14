package nl.soccar.gameserver.message;

import nl.soccar.gameserver.message.handler.PlayerLeaveSessionMessageHandler;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_LEAVE_SESSION_MESSAGE_ID, handler = PlayerLeaveSessionMessageHandler.class)
public class PlayerLeaveSessionMessage extends Message {

    @Override
    public int getId() {
        return MessageConstants.PLAYER_LEAVE_SESSION_MESSAGE_ID;
    }

}
