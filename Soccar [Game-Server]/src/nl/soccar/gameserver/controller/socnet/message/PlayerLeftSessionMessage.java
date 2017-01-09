package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.PlayerLeftSessionMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for leaving a player of the Game.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_LEFT_SESSION_MESSAGE_ID, handler = PlayerLeftSessionMessageHandler.class)
public final class PlayerLeftSessionMessage extends Message {

    private final int playerId;

    /**
     * Initilizes the PlayerLeftSessionMessage class.
     *
     * @param playerId The id of the player, not null.
     */
    public PlayerLeftSessionMessage(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Gets the id of the player.
     *
     * @return int The id of the player.
     */
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_LEFT_SESSION_MESSAGE_ID;
    }

}
