package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.SwitchTeamMessageHandler;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SWITCH_TEAM_MESSAGE_ID, handler = SwitchTeamMessageHandler.class)
public final class SwitchTeamMessage extends Message {

    private final int playerId;
    private final TeamColour team;

    public SwitchTeamMessage(int playerId, TeamColour team) {
        this.playerId = playerId;
        this.team = team;
    }

    public SwitchTeamMessage() {
        playerId = -1;
        team = null;
    }

    public int getPlayerId() {
        return playerId;
    }

    public TeamColour getTeamColour() {
        return team;
    }

    @Override
    public int getId() {
        return MessageConstants.SWITCH_TEAM_MESSAGE_ID;
    }

}
