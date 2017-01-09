package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.SpawnObstacleMessageHandler;
import nl.soccar.library.enumeration.ObstacleType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for spawning a Obstacle on the field of the game.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SPAWN_OBSTACLE_MESSAGE_ID, handler = SpawnObstacleMessageHandler.class)
public final class SpawnObstacleMessage extends Message {

    private final float x;
    private final float y;
    private final float angle;

    private final float width;
    private final float height;
    private final ObstacleType type;

    /**
     * Intiliazes the SpawnObstacleMessage class.
     * 
     * @param x The X position of the obstacle, not null.
     * @param y The Y position of the obstacle, not null.
     * @param angle The angle of the obstacle, not null.
     * @param width The width of the obstacle, not null.
     * @param height The height of the obstacle, not null.
     * @param type The type of the obstacle, not null.
     */
    public SpawnObstacleMessage(float x, float y, float angle, float width, float height, ObstacleType type) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    /**
     * Gets the X position of the obstacle.
     * 
     * @return float The X position of the obstacle.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the Y position of the obstacle.
     * 
     * @return float The Y position of the obstacle.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the angle of the obstacle.
     * 
     * @return float The angle of the obstacle.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Gets the width of the obstacle.
     * 
     * @return float The width of the obstacle.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets the height of the obstacle.
     * 
     * @return float The height of the obstacle.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Gets type of the obstacle.
     * 
     * @return ObstacleType The type of the obstacle.
     */
    public ObstacleType getObstacleType() {
        return type;
    }

    @Override
    public int getId() {
        return MessageConstants.SPAWN_OBSTACLE_MESSAGE_ID;
    }

}
