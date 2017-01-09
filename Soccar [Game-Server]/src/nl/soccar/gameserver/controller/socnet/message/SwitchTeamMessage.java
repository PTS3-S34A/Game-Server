package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.SwitchTeamMessageHandler;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for switching a team in the game.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SWITCH_TEAM_MESSAGE_ID, handler = SwitchTeamMessageHandler.class)
public final class SwitchTeamMessage extends Message {

    private final int playerId;
    private final TeamColour team;

    /**
     * Intializes the SwitchTeamMessage class.
     *
     * @param playerId The id of the player, not null.
     * @param team The team colour of the player, not null.
     */
    public SwitchTeamMessage(int playerId, TeamColour team) {
        this.playerId = playerId;
        this.team = team;
    }

    /**
     * Initializes the SwitchTeamMessage class.
     */
    public SwitchTeamMessage() {
        playerId = -1;
        team = null;
    }

    /**
     * Gets the id of the player.
     *
     * @return int The id of the player.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets the team colour of the player.
     *
     * @return TeamColour The team colour of the player.
     */
    public TeamColour getTeamColour() {
        return team;
    }

    @Override
    public int getId() {
        return MessageConstants.SWITCH_TEAM_MESSAGE_ID;
    }

}
