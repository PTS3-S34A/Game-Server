package nl.soccar.gameserver.controller.socnet.message;

import nl.soccar.gameserver.controller.socnet.message.handler.RegisterPlayerMessageHandler;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for Registering a player to the game.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.REGISTER_PLAYER_MESSAGE_ID, handler = RegisterPlayerMessageHandler.class)
public final class RegisterPlayerMessage extends Message {

    private final String username;
    private final CarType carType;

    /**
     * Initializes the RegisterPlaterMessage class.
     *
     * @param username The username of the player, not null/empty.
     * @param carType The car type of the player, not null.
     */
    public RegisterPlayerMessage(String username, CarType carType) {
        this.username = username;
        this.carType = carType;
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
     * Gets the car type of the player.
     *
     * @return CarType The car type of the player.
     */
    public CarType getCarType() {
        return carType;
    }

    @Override
    public int getId() {
        return MessageConstants.REGISTER_PLAYER_MESSAGE_ID;
    }

}
