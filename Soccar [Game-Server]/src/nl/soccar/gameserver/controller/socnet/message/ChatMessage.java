package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.ChatMessageHandler;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for for a Chat Message.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.CHAT_MESSAGE_ID, handler = ChatMessageHandler.class)
public final class ChatMessage extends Message {

    private final int playerId;
    private final Privilege privilege;
    private final String message;

    /**
     * Intializes the ChatMessage class.
     *
     * @param playerId The id of the player, not null.
     * @param privilege The privilige of the player, not null.
     * @param message The message of the chat message, not null/empty.
     */
    public ChatMessage(int playerId, Privilege privilege, String message) {
        this.playerId = playerId;
        this.privilege = privilege;
        this.message = message;
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
     * Gets the privilege of the player.
     *
     * @return privilege Privilige of the player.
     */
    public Privilege getPrivilege() {
        return privilege;
    }

    /**
     * Gets the Message of the Chat Message.
     *
     * @return String Message of the Chat Message.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public int getId() {
        return MessageConstants.CHAT_MESSAGE_ID;
    }

}
