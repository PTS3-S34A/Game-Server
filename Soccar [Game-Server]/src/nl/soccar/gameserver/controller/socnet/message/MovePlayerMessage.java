package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.MovePlayerMessageHandler;
import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.ThrottleAction;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * MEssage for moving a player in the Game.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.MOVE_PLAYER_MESSAGE_ID, handler = MovePlayerMessageHandler.class)
public final class MovePlayerMessage extends Message {

    private final int playerId;
    private final SteerAction steerAction;
    private final HandbrakeAction handbrakeAction;
    private final ThrottleAction throttleAction;

    /**
     * Intializes the MovePlayerMessage class.
     *
     * @param playerId The id of the player, not null.
     * @param steerAction The steer action of the player, not null.
     * @param handbrakeAction The handbrake action of the player, not null.
     * @param throttleAction The throttle action of the player, not null.
     */
    public MovePlayerMessage(int playerId, SteerAction steerAction, HandbrakeAction handbrakeAction, ThrottleAction throttleAction) {
        this.playerId = playerId;
        this.steerAction = steerAction;
        this.handbrakeAction = handbrakeAction;
        this.throttleAction = throttleAction;
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
     * Gets the steer action of the player.
     *
     * @return SteerAction The steer action of the player.
     */
    public SteerAction getSteerAction() {
        return steerAction;
    }

    /**
     * Gets the handbrake action of the player.
     *
     * @return HandbrakAction The handbrake action of the player.
     */
    public HandbrakeAction getHandbrakAction() {
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
        return MessageConstants.MOVE_PLAYER_MESSAGE_ID;
    }

}
