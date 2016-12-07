package nl.soccar.gameserver.message;

import nl.soccar.gameserver.message.handler.PlayerLeftSessionMessageHandler;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_LEFT_SESSION_MESSAGE_ID, handler = PlayerLeftSessionMessageHandler.class)
public final class PlayerLeftSessionMessage extends Message {

    private final String username;
    private final TeamColour team;

    public PlayerLeftSessionMessage(String username, TeamColour team) {
        this.username = username;
        this.team = team;
    }

    public String getUsername() {
        return username;
    }

    public TeamColour getTeam() {
        return team;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_LEFT_SESSION_MESSAGE_ID;
    }

}
