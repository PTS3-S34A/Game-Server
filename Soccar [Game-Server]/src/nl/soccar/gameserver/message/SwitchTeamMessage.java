package nl.soccar.gameserver.message;

import nl.soccar.gameserver.message.handler.SwitchTeamMessageHandler;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SWITCH_TEAM_MESSAGE_ID, handler = SwitchTeamMessageHandler.class)
public final class SwitchTeamMessage extends Message {

    private final String username;
    private final TeamColour team;
    
    public SwitchTeamMessage(String username, TeamColour team) {
        this.username = username;
        this.team = team;
    }
    
    public SwitchTeamMessage() {
        username = null;
        team = null;
    }
    
    public String getUsername() {
        return username;
    }
    
    public TeamColour getTeamColour() {
        return team;
    }
    
    @Override
    public int getId() {
        return MessageConstants.SWITCH_TEAM_MESSAGE_ID;
    }

}
