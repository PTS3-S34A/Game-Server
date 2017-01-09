package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.PlayerChangedGameStatusMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for changing the gamestatus of the game.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_CHANGED_GAME_STATUS_MESSAGE_ID, handler = PlayerChangedGameStatusMessageHandler.class)
public final class PlayerChangedGameStatusMessage extends Message {

    private final Status status;

    /**
     * Initializes the PlayerChangedGameStatusMessage class.
     * 
     * @param status The status of the game.
     */
    public PlayerChangedGameStatusMessage(Status status) {
        this.status = status;
    }

    /**
     * Gets the status of the game.
     * 
     * @return Status The game status of the game.
     */
    public Status getStatus() {
        return status;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_CHANGED_GAME_STATUS_MESSAGE_ID;
    }

    /**
     * Enum for the status of the game.
     */
    public enum Status {
        GAME_PAUSED,
        GAME_RUNNING
    }

}
