package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.PlayerMovedMessageHandler;
import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.ThrottleAction;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for moving a player in the game.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_MOVED_MESSAGE_ID, handler = PlayerMovedMessageHandler.class)
public final class PlayerMovedMessage extends Message {

    private final SteerAction steerAction;
    private final HandbrakeAction handbrakeAction;
    private final ThrottleAction throttleAction;

    /**
     * Initializes the PlayerMovedMessage class.
     *
     * @param steerAction The steer action of the player, not null.
     * @param handbrakeAction The handbrake action of the player, not null.
     * @param throttleAction The throttle action of the player, not null.
     */
    public PlayerMovedMessage(SteerAction steerAction, HandbrakeAction handbrakeAction, ThrottleAction throttleAction) {
        this.steerAction = steerAction;
        this.handbrakeAction = handbrakeAction;
        this.throttleAction = throttleAction;
    }

    /**
     * Gets the steeraction of the player.
     *
     * @return SteerAction The steer action of the player.
     */
    public SteerAction getSteerAction() {
        return steerAction;
    }

    /**
     * Gets the handbrake action of the player.
     *
     * @return HandbrakeAction The handbrake action of the player.
     */
    public HandbrakeAction gethandbrakaction() {
        return handbrakeAction;
    }

    /**
     * Gets the throttle action of the player.
     *
     * @return ThrottleAction The throttle action of the player.
     */
    public ThrottleAction getThrottleAction() {
        return throttleAction;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_MOVED_MESSAGE_ID;
    }

}
