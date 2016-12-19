package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.PlayerJoinedSessionMessageHandler;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_JOINED_SESSION_MESSAGE_ID, handler = PlayerJoinedSessionMessageHandler.class)
public final class PlayerJoinedSessionMessage extends Message {

    private final int playerId;
    private final String username;
    private final Privilege privilege;

    private final CarType carType;
    private final TeamColour team;

    public PlayerJoinedSessionMessage(int playerId, String username, Privilege privilege, CarType carType, TeamColour team) {
        this.playerId = playerId;
        this.username = username;
        this.privilege = privilege;

        this.carType = carType;
        this.team = team;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getUsername() {
        return username;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public CarType getCarType() {
        return carType;
    }

    public TeamColour getTeam() {
        return team;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_JOINED_SESSION_MESSAGE_ID;
    }

}
