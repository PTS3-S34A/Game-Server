package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.SpawnBallMessageHandler;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SPAWN_BALL_MESSAGE_ID, handler = SpawnBallMessageHandler.class)
public final class SpawnBallMessage extends Message {

    private final float x;
    private final float y;
    private final float angle;

    private final BallType type;

    public SpawnBallMessage(float x, float y, float angle, BallType type) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public BallType getBallType() {
        return type;
    }

    @Override
    public int getId() {
        return MessageConstants.SPAWN_BALL_MESSAGE_ID;
    }

}
