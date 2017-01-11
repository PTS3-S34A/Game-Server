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

    /**
     * Initializes the event message.
     *
     * @param type     The type of Event that should be sent, not null.
     * @param playerId The id of the Player that initiated the Event.
     */
    public EventMessage(EventType type, int playerId) {
        this.type = type;
        this.playerId = playerId;
    }

    /**
     * Gets the type of the Event that occurred.
     *
     * @return The type of the Event.
     */
    public EventType getEventType() {
        return type;
    }

    /**
     * Gets the id of the player that initiated the Event.
     *
     * @return The id of the Player.
     */
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public int getId() {
        return MessageConstants.EVENT_MESSAGE;
    }

}
