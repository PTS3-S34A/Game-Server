package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.SpawnBallMessageHandler;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for spawning the ball on the field of the game.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SPAWN_BALL_MESSAGE_ID, handler = SpawnBallMessageHandler.class)
public final class SpawnBallMessage extends Message {

    private final float x;
    private final float y;
    private final float angle;

    private final BallType type;

    /**
     * Initializes the SpawnBallMessage class.
     * 
     * @param x The X position of the ball, not null.
     * @param y The Y position of the ball, not null.
     * @param angle The angle of the ball, not null.
     * @param type The ball type of the ball, not null.
     */
    public SpawnBallMessage(float x, float y, float angle, BallType type) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.type = type;
    }

    /**
     * Gets the X position of the ball.
     * 
     * @return float The X position of the ball.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the Y position of the ball.
     * 
     * @return float The Y position of the ball.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the angle of the ball.
     * 
     * @return float The angles of the ball.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Gets the ball type of the ball.
     * 
     * @return BallType The ball type of the ball.
     */
    public BallType getBallType() {
        return type;
    }

    @Override
    public int getId() {
        return MessageConstants.SPAWN_BALL_MESSAGE_ID;
    }

}
