package nl.soccar.gameserver.message;

import nl.soccar.gameserver.message.handler.PlayerLeaveSessionMessageHandler;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageEvent;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = 5, handler = PlayerLeaveSessionMessageHandler.class)
public class PlayerLeaveSessionMessage extends Message {

    private static final int PLAYER_LEAVE_SESSION_MESSAGE = 5;

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
        return PLAYER_LEAVE_SESSION_MESSAGE;
    }

}
