package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.GameTimeSyncMessageHandler;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for syncing the Game Time.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SYNC_GAME_TIME_MESSAGE_ID, handler = GameTimeSyncMessageHandler.class)
public class GameTimeSyncMessage extends Message{

    private final int gameTime;
    private final long currentTime;
    
    /**
     * Initializes the GameTimeSyncMessage class.
     * 
     * @param gameTime The gametime, not null.
     * @param currentTime The current time, not null.
     */
    public GameTimeSyncMessage(int gameTime, long currentTime) {
        this.gameTime = gameTime;
        this.currentTime = currentTime;
    }
    
    /**
     * Gets the game time.
     * 
     * @return int The game time.
     */
    public int getGameTime() {
        return gameTime;
    }
    
    /**
     * Gets the current time.
     * 
     * @return int The current time.
     */
    public long getCurrentTime() {
        return currentTime;
    }
    
    @Override
    public int getId() {
        return MessageConstants.SYNC_GAME_TIME_MESSAGE_ID;
    }
    
}
