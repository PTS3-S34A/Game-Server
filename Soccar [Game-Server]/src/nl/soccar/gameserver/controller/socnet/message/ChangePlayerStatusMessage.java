package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.ChangePlayerStatusMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.CHANGE_PLAYER_STATUS_MESSAGE_ID, handler = ChangePlayerStatusMessageHandler.class)
public final class ChangePlayerStatusMessage extends Message {

    private final Status status;

    public ChangePlayerStatusMessage(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public int getId() {
        return MessageConstants.CHANGE_PLAYER_STATUS_MESSAGE_ID;
    }

    public enum Status {
        REQUEST_WORLD_OBJECTS,
        READY_TO_PLAY
    }

}
