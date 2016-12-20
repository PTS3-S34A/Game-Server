package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.SpawnCarMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SPAWN_CAR_MESSAGE_ID, handler = SpawnCarMessageHandler.class)
public final class SpawnCarMessage extends Message {

    private final int playerId;
    private final float x;
    private final float y;
    private final float angle;

    public SpawnCarMessage(int playerId, float x, float y, float angle) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.angle = angle;
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

    @Override
    public int getId() {
        return MessageConstants.SPAWN_CAR_MESSAGE_ID;
    }

}
