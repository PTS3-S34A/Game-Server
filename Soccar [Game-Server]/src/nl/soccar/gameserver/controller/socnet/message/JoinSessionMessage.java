package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.JoinSessionMessageHandler;
import nl.soccar.library.GameSettings;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for joining a Session.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.JOIN_SESSION_MESSAGE_ID, handler = JoinSessionMessageHandler.class)
public final class JoinSessionMessage extends Message {

    private final String roomName;
    private final String password;
    private final Status status;
    private final int capacity;
    private final GameSettings settings;

    /**
     * Initializes the JoinSessionMessage class.
     *
     * @param status The status of Joining a Session, not null.
     */
    public JoinSessionMessage(Status status) {
        this.status = status;
        roomName = null;
        password = null;
        capacity = -1;
        settings = null;
    }

    /**
     * Intializes the JoinSessionMessage class.
     * 
     * @param roomName The given room name, not null.
     * @param password The given password, not null.
     */
    public JoinSessionMessage(String roomName, String password) {
        this.roomName = roomName;
        this.password = password;

        status = null;
        capacity = -1;
        settings = null;
    }

    /**
     * Intializes the JoinSessionMessage class.
     * 
     * @param status The status of joining a Session, not null.
     * @param roomName The roomname, not null.
     * @param capacity The capcity of the room, not null.
     * @param settings The game settings, not null.
     */
    public JoinSessionMessage(Status status, String roomName, int capacity, GameSettings settings) {
        this.status = status;
        this.roomName = roomName;
        password = "";
        this.capacity = capacity;
        this.settings = settings;
    }

    /**
     * Gets the Room name.
     * 
     * @return String The room name.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Gets the password of the Room.
     * 
     * @return String The password of the Room.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the status of the Joiningsession message.
     * 
     * @return Status The stauts of the JoiningSession message.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Gets the capacity of the Room.
     * 
     * @return int The capcity of the Room.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets the gamesettings of the game.
     * 
     * @return The gamesettings of the game.
     */
    public GameSettings getGameSettings() {
        return settings;
    }

    @Override
    public int getId() {
        return MessageConstants.JOIN_SESSION_MESSAGE_ID;
    }

    /**
     * Enum for the satus of joining a session.
     */
    public enum Status {
        SUCCESS,
        SESSION_NON_EXISTENT,
        INVALID_PASSWORD,
        CAPACITY_OVERFLOW,
        USERNAME_EXISTS
    }

}
