package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.PlayerSyncMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SYNC_POSITION_PLAYER_MESSAGE_ID, handler = PlayerSyncMessageHandler.class)
public final class PlayerSyncMessage extends Message {

    private final int playerId;

    private final float x;
    private final float y;
    private final float angle;

    private final float linearVelocityX;
    private final float linearVelocityY;

    private final float angularVelocity;

    public PlayerSyncMessage(int playerId, float x, float y, float angle, float linearVelocityX, float linearVelocityY, float angularVelocity) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.linearVelocityX = linearVelocityX;
        this.linearVelocityY = linearVelocityY;
        this.angularVelocity = angularVelocity;
    }

    public int getPlayerId() {
        return playerId;
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
        return MessageConstants.SYNC_POSITION_PLAYER_MESSAGE_ID;
    }

}
