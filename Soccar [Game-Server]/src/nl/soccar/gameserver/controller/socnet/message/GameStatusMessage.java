package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.GameStatusMessageHandler;
import nl.soccar.library.enumeration.GameStatus;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for notifying the GameStatus (purely estethical).
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.GAME_STATUS_MESSAGE_ID, handler = GameStatusMessageHandler.class)
public final class GameStatusMessage extends Message {

    private final GameStatus status;

    /**
     * Initializes the GameStatusMessage without an actual GameStatus, this way requests can be initiated.
     */
    public GameStatusMessage() {
        this(null);
    }

    /**
     * Initializes the GameStatusMessage.
     *
     * @param status The GameStatus of the Game.
     */
    public GameStatusMessage(GameStatus status) {
        this.status = status;
    }

    /**
     * Gets the GameStatus of the Game.
     *
     * @return The GameStatus of the Game.
     */
    public GameStatus getGameStatus() {
        return status;
    }

    @Override
    public int getId() {
        return MessageConstants.GAME_STATUS_MESSAGE_ID;
    }

}
