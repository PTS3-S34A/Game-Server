package nl.soccar.gameserver.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.gameserver.SessionController;
import nl.soccar.gameserver.message.PlayerLeftSessionMessage;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author PTS34A
 */
public final class PlayerLeftSessionMessageHandler extends MessageHandler<PlayerLeftSessionMessage> {

    private static final Logger LOGGER = Logger.getLogger(PlayerLeftSessionMessage.class.getSimpleName());

    @Override
    protected void handle(Connection connection, PlayerLeftSessionMessage message) throws Exception {
        Player player = connection.getPlayer();
        if (player == null) {
            connection.close();
            return;
        }

        Session session = player.getCurrentSession();
        if (session == null) {
            connection.close();
            return;
        }

        if (!SessionController.getInstance().leaveSession(player, session)) {
            connection.close();
            return;
        }

        LOGGER.log(Level.INFO, "Player {0} left session {1}.", new String[] { player.getUsername(), session.getRoom().getName() });
    }

    @Override
    protected void encode(Connection connection, PlayerLeftSessionMessage message, ByteBuf buf) throws Exception {
        ByteBufUtilities.writeString(message.getUsername(), buf);
        ByteBufUtilities.writeString(message.getTeam().name(), buf);
    }

    @Override
    protected PlayerLeftSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding not supported for Server.");
    }

}
