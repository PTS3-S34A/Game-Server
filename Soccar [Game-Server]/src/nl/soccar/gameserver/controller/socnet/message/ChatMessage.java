package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.ChatMessageHandler;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.CHAT_MESSAGE_ID, handler = ChatMessageHandler.class)
public final class ChatMessage extends Message {

    private final int playerId;
    private final Privilege privilege;
    private final String message;

    public ChatMessage(int playerId, Privilege privilege, String message) {
        this.playerId = playerId;
        this.privilege = privilege;
        this.message = message;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int getId() {
        return MessageConstants.CHAT_MESSAGE_ID;
    }

}
