package nl.soccar.gameserver.message.handler;

import io.netty.buffer.ByteBuf;
import java.util.logging.Level;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.gameserver.message.PlayerLeftSessionMessage;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

import java.util.logging.Logger;

/**
 * @author PTS34A
 */
public final class PlayerLeftSessionMessageHandler extends MessageHandler<PlayerLeftSessionMessage> {

    private static final Logger LOGGER = Logger.getLogger(PlayerLeftSessionMessage.class.getSimpleName());

    @Override
    protected void handle(Connection connection, PlayerLeftSessionMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling not supported for Server.");
    }

    @Override
    protected void encode(Connection connection, PlayerLeftSessionMessage message, ByteBuf buf) throws Exception {
        LOGGER.log(Level.INFO, "Sended playerLeftMessage to {0}.", connection.getPlayer().getUsername());
        ByteBufUtilities.writeString(message.getUsername(), buf);
        ByteBufUtilities.writeString(message.getTeam().name(), buf);
    }

    @Override
    protected PlayerLeftSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding not supported for Server.");
    }

}
