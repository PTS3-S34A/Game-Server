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

    private final String username;
    private final TeamColour colour;

    public PlayerLeaveSessionMessage(String username, TeamColour colour) {
        this.username = username;
        this.colour = colour;
    }

    public String getUsername() {
        return username;
    }

    public TeamColour getTeamColour() {
        return colour;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_LEAVE_SESSION_MESSAGE_ID;
    }

}
