package nl.soccar.gameserver.message;

import nl.soccar.gameserver.message.handler.MovePlayerMessageHandler;
import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.ThrottleAction;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.MOVE_PLAYER_MESSAGE_ID, handler = MovePlayerMessageHandler.class)
public final class MovePlayerMessage extends Message {

    private final String username;
    private final SteerAction steerAction;
    private final HandbrakeAction handbrakeAction;
    private final ThrottleAction throttleAction;

    public MovePlayerMessage(String username, SteerAction steerAction, HandbrakeAction handbrakeAction, ThrottleAction throttleAction) {
        this.username = username;
        this.steerAction = steerAction;
        this.handbrakeAction = handbrakeAction;
        this.throttleAction = throttleAction;
    }

    public String getUsername() {
        return username;
    }

    public SteerAction getSteerAction() {
        return steerAction;
    }

    public HandbrakeAction getHandbrakAction() {
        return handbrakeAction;
    }

    public ThrottleAction getThrottleAction() {
        return throttleAction;
    }

    @Override
    public int getId() {
        return MessageConstants.MOVE_PLAYER_MESSAGE_ID;
    }

}
