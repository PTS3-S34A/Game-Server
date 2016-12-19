package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.BallSyncMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

@MessageEvent(id = MessageConstants.SYNC_POSITION_BALL_MESSAGE_ID, handler = BallSyncMessageHandler.class)
public final class BallSyncMessage extends Message {

    private final float x;
    private final float y;

    private final float linearVelocityX;
    private final float linearVelocityY;

    private final float angularVelocity;

    public BallSyncMessage(float x, float y, float linearVelocityX, float linearVelocityY, float angularVelocity) {
        this.x = x;
        this.y = y;
        this.linearVelocityX = linearVelocityX;
        this.linearVelocityY = linearVelocityY;
        this.angularVelocity = angularVelocity;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getLinearVelocityX() {
        return linearVelocityX;
    }

    public float getLinearVelocityY() {
        return linearVelocityY;
    }

    public float getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public int getId() {
        return MessageConstants.SYNC_POSITION_BALL_MESSAGE_ID;
    }

}
