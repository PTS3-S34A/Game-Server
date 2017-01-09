package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.BallSyncMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for the ball synchronization.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SYNC_POSITION_BALL_MESSAGE_ID, handler = BallSyncMessageHandler.class)
public final class BallSyncMessage extends Message {

    private final float x;
    private final float y;

    private final float linearVelocityX;
    private final float linearVelocityY;

    private final float angularVelocity;

    /**
     * Intitializes the BallSyncMessage class.
     *
     * @param x The X posiition of the ball, not null.
     * @param y The Y position of the ball, not null.
     * @param linearVelocityX The linear X velocity of the ball, not null.
     * @param linearVelocityY The linear Y velocity of the ball, not null.
     * @param angularVelocity The angular velocity of the ball, not null.
     */
    public BallSyncMessage(float x, float y, float linearVelocityX, float linearVelocityY, float angularVelocity) {
        this.x = x;
        this.y = y;
        this.linearVelocityX = linearVelocityX;
        this.linearVelocityY = linearVelocityY;
        this.angularVelocity = angularVelocity;
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
     * @return floatvThe Y position of the ball.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the linear X velocity of the ball.
     *
     * @return float The linear X velocity of the ball.
     */
    public float getLinearVelocityX() {
        return linearVelocityX;
    }

    /**
     * Gets the linear Y velocity of the ball.
     *
     * @return float The linear Y velocity of the ball.
     */
    public float getLinearVelocityY() {
        return linearVelocityY;
    }

    /**
     * Gets the angular velocity of the ball.
     *
     * @return float The angular velocity of the ball.
     */
    public float getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public int getId() {
        return MessageConstants.SYNC_POSITION_BALL_MESSAGE_ID;
    }

}
