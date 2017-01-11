package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.EventMessageHandler;
import nl.soccar.library.enumeration.EventType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for changing host.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.EVENT_MESSAGE, handler = EventMessageHandler.class)
public final class EventMessage extends Message {

    private final EventType type;
    private final int playerId;

    public EventMessage(EventType type, int playerId) {
        this.type = type;
        this.playerId = playerId;
    }

    public EventType getEventType() {
        return type;
    }

    public int getPlayerId() {
        return playerId;
    }

    @Override
    public int getId() {
        return MessageConstants.EVENT_MESSAGE;
    }

}
