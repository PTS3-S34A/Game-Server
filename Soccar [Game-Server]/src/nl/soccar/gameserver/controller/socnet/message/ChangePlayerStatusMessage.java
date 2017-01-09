package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.ChangePlayerStatusMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for changing the player status of the given player.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.CHANGE_PLAYER_STATUS_MESSAGE_ID, handler = ChangePlayerStatusMessageHandler.class)
public final class ChangePlayerStatusMessage extends Message {

    private final Status status;

    /**
     * Initializes the ChangePlayerStatusMessage class.
     * 
     * @param status The status of the player, not null.
     */
    public ChangePlayerStatusMessage(Status status) {
        this.status = status;
    }

    /**
     * Gets the Status of the player.
     * 
     * @return status The status of the player.
     */
    public Status getStatus() {
        return status;
    }

    @Override
    public int getId() {
        return MessageConstants.CHANGE_PLAYER_STATUS_MESSAGE_ID;
    }

    /**
     * Enum for the status of the player.
     */
    public enum Status {
        REQUEST_WORLD_OBJECTS,
        READY_TO_PLAY
    }

}
