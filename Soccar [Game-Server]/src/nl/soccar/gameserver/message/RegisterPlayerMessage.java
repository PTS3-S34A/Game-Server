package nl.soccar.gameserver.message;

import nl.soccar.gameserver.message.handler.RegisterPlayerMessageHandler;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageEvent;

/**
 * @author PTS34A
 */
@MessageEvent(id = 1, handler = RegisterPlayerMessageHandler.class)
public final class RegisterPlayerMessage extends Message {

    private final String username;
    private final CarType carType;

    public RegisterPlayerMessage(String username, CarType carType) {
        this.username = username;
        this.carType = carType;
    }

    public String getUsername() {
        return username;
    }

    public CarType getCarType() {
        return carType;
    }

    @Override
    public int getId() {
        return 1;
    }

}
