package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.ChangeHostMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for changing host.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.CHANGE_HOST_MESSAGE_ID, handler = ChangeHostMessageHandler.class)
public final class ChangeHostMessage extends Message {

    private final int playerId;

    /**
     * Initializes this message.
     *
     * @param playerId The id of the new host.
     */
    public ChangeHostMessage(int playerId) {
        this.playerId = playerId;
    }

    /**
     * The username of the new host.
     *
     * @return The id of the new host.
     */
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public int getId() {
        return MessageConstants.CHANGE_HOST_MESSAGE_ID;
    }

}
