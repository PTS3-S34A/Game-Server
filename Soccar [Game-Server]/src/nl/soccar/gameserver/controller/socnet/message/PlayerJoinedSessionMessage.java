package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.PlayerJoinedSessionMessageHandler;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for joining a player to the game.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_JOINED_SESSION_MESSAGE_ID, handler = PlayerJoinedSessionMessageHandler.class)
public final class PlayerJoinedSessionMessage extends Message {

    private final int playerId;
    private final String username;
    private final Privilege privilege;

    private final CarType carType;
    private final TeamColour team;

    /**
     * Initializes the PlayerJoinedSessionMessage class.
     *
     * @param playerId The id from the player, not null.
     * @param username The username of the player, not null.
     * @param privilege The privilege of the player, not null.
     * @param carType The car type of the player, not null.
     * @param team The team colour of the player, not null.
     */
    public PlayerJoinedSessionMessage(int playerId, String username, Privilege privilege, CarType carType, TeamColour team) {
        this.playerId = playerId;
        this.username = username;
        this.privilege = privilege;

        this.carType = carType;
        this.team = team;
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
     * Gets the username of the player.
     *
     * @return String The username of the player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the privilege of the player.
     *
     * @return Privilege The privilege of the player.
     */
    public Privilege getPrivilege() {
        return privilege;
    }

    /**
     * Gets the car type of the player.
     *
     * @return CarType The car type of the player.
     */
    public CarType getCarType() {
        return carType;
    }

    /**
     * Gets the Team Colour of the player.
     *
     * @return TeamColour The team colour of the player.
     */
    public TeamColour getTeam() {
        return team;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_JOINED_SESSION_MESSAGE_ID;
    }

}
