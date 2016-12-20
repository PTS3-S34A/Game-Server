package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.SpawnObstacleMessageHandler;
import nl.soccar.library.enumeration.ObstacleType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
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

    public SpawnObstacleMessage(float x, float y, float angle, float width, float height, ObstacleType type) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.width = width;
        this.height = height;
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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public ObstacleType getObstacleType() {
        return type;
    }

    @Override
    public int getId() {
        return MessageConstants.SPAWN_OBSTACLE_MESSAGE_ID;
    }

}
