package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.ChangeHostMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for the ball synchronization.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.CHANGE_HOST_MESSAGE_ID, handler = ChangeHostMessageHandler.class)
public final class ChangeHostMessage extends Message {

    private final String username;

    /**
     * Initializes this message.
     *
     * @param username The username of the new host, not null.
     */
    public ChangeHostMessage(String username) {
        this.username = username;
    }

    /**
     * The username of the new host.
     *
     * @return The username of the new host, not null.
     */
    public String getUsername() {
        return username;
    }

    @Override
    public int getId() {
        return MessageConstants.CHANGE_HOST_MESSAGE_ID;
    }

}
