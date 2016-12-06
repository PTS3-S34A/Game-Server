package nl.soccar.gameserver.message.handler;

import io.netty.buffer.ByteBuf;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.gameserver.message.RegisterPlayerMessage;
import nl.soccar.library.Player;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 *
 * @author PTS34A
 */
public final class RegisterPlayerMessageHandler extends MessageHandler<RegisterPlayerMessage> {

    private final static Logger LOGGER = Logger.getLogger(RegisterPlayerMessageHandler.class.getSimpleName());

    @Override
    protected void handle(Connection connection, RegisterPlayerMessage message) throws Exception {
        Player player = new Player(message.getUsername(), Privilege.NORMAL, message.getCarType());

        connection.getNode().addConnection(player, connection);
        connection.setPlayer(player);

        LOGGER.log(Level.INFO, "Player {0} registered.", player.getUsername());
    }

    @Override
    protected void encode(Connection connection, RegisterPlayerMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding not supported for Server.");
    }

    @Override
    protected RegisterPlayerMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String username = ByteBufUtilities.readString(buf);
        CarType type = CarType.valueOf(ByteBufUtilities.readString(buf));

        return new RegisterPlayerMessage(username, type);
    }

}
