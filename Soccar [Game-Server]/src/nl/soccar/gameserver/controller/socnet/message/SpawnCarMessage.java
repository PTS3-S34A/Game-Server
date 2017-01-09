package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.SpawnCarMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message fot spawning the Car on the field of the game.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SPAWN_CAR_MESSAGE_ID, handler = SpawnCarMessageHandler.class)
public final class SpawnCarMessage extends Message {

    private final int playerId;
    private final float x;
    private final float y;
    private final float angle;

    /**
     * Intiliazes the SpawnCarMessage class.
     *
     * @param playerId The id of the player, not null.
     * @param x The X position of the car, not null.
     * @param y The Y position of the car, not null.
     * @param angle The angle of the car, not null.
     */
    public SpawnCarMessage(int playerId, float x, float y, float angle) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.angle = angle;
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
     * Gets the X position of the car.
     *
     * @return float The X position of the car.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the Y position of the car.
     *
     * @return float The Y position of the car.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the angle of the car.
     *
     * @return float The angle of the car.
     */
    public float getAngle() {
        return angle;
    }

    @Override
    public int getId() {
        return MessageConstants.SPAWN_CAR_MESSAGE_ID;
    }

}
