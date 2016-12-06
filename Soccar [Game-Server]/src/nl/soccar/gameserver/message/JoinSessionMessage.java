package nl.soccar.gameserver.message;

import nl.soccar.gameserver.message.handler.JoinSessionMessageHandler;
import nl.soccar.library.GameSettings;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageEvent;

/**
 * @author PTS34A
 */
@MessageEvent(id = 2, handler = JoinSessionMessageHandler.class)
public final class JoinSessionMessage extends Message {

    public enum Status {
        SUCCESS,
        SESSION_NON_EXISTENT,
        INVALID_PASSWORD,
        CAPACITY_OVERFLOW,
        USERNAME_EXISTS
    }

    private final String roomName;
    private final String password;

    private final Status status;
    private final int capacity;
    private final GameSettings settings;

    public JoinSessionMessage(Status status) {
        this.status = status;
        roomName = null;
        password = null;
        capacity = -1;
        settings = null;
    }

    public JoinSessionMessage(String roomName, String password) {
        this.roomName = roomName;
        this.password = password;

        status = null;
        capacity = -1;
        settings = null;
    }

    public JoinSessionMessage(Status status, String roomName, int capacity, GameSettings settings) {
        this.status = status;
        this.roomName = roomName;
        password = "";
        this.capacity = capacity;
        this.settings = settings;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getPassword() {
        return password;
    }

    public Status getStatus() {
        return status;
    }

    public int getCapacity() {
        return capacity;
    }

    public GameSettings getGameSettings() {
        return settings;
    }

    @Override
    public int getId() {
        return 2;
    }

}
