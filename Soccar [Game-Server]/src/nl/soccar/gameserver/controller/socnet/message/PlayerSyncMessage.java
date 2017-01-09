package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.PlayerSyncMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for syncing a player in the game.
 *
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

    /**
     * Intializes the PlayerSyncMessage class.
     *
     * @param playerId The id of the player, not null.
     * @param x The X position of the player, not null.
     * @param y The Y position of the player, not null.
     * @param angle The angle of the player, not null.
     * @param linearVelocityX The linear X velocity of the player, not null.
     * @param linearVelocityY The linear Y velocity of the player, not null.
     * @param angularVelocity The angular velocity of the player, not null.
     */
    public PlayerSyncMessage(int playerId, float x, float y, float angle, float linearVelocityX, float linearVelocityY, float angularVelocity) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.linearVelocityX = linearVelocityX;
        this.linearVelocityY = linearVelocityY;
        this.angularVelocity = angularVelocity;
    }

    /**
     * Gets the id of the palyer.
     *
     * @return int The id of the player.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets the X position of the player.
     *
     * @return float The X position of the player.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the Y position of the player.
     *
     * @return float The Y position of the player.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the Angle of the player.
     *
     * @return float The angle of the player.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Gets the linear X velocity of the player.
     *
     * @return float The linear X velocity of the player.
     */
    public float getLinearVelocityX() {
        return linearVelocityX;
    }

    /**
     * Gets the linear Y velocity of the player.
     *
     * @return float The linear Y velocity of the player.
     */
    public float getLinearVelocityY() {
        return linearVelocityY;
    }

    /**
     * Gets the angular velocity of the player.
     *
     * @return float The angular velocity of the player.
     */
    public float getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public int getId() {
        return MessageConstants.SYNC_POSITION_PLAYER_MESSAGE_ID;
    }

}
