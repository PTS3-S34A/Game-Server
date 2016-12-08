package nl.soccar.gameserver.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.gameserver.SessionController;
import nl.soccar.gameserver.message.JoinSessionMessage;
import nl.soccar.library.GameSettings;
import nl.soccar.library.Player;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PTS34A
 */
public final class JoinSessionMessageHandler extends MessageHandler<JoinSessionMessage> {

    private static final Logger LOGGER = Logger.getLogger(JoinSessionMessageHandler.class.getSimpleName());

    @Override
    protected void handle(Connection connection, JoinSessionMessage message) throws Exception {
        Player player = connection.getPlayer();
        if (player == null) {
            connection.close();
            return;
        }

        String roomName = message.getRoomName();
        JoinSessionMessage.Status status = SessionController.getInstance().joinSession(player, roomName, message.getPassword());

        if (status != JoinSessionMessage.Status.SUCCESS) {
            connection.send(new JoinSessionMessage(status));
            return;
        }

        LOGGER.log(Level.INFO, "Player {0} joined session {1}.", new String[]{player.getUsername(), roomName});
    }

    @Override
    protected void encode(Connection connection, JoinSessionMessage message, ByteBuf buf) throws Exception {
        JoinSessionMessage.Status status = message.getStatus();
        if (status != JoinSessionMessage.Status.SUCCESS) {
            ByteBufUtilities.writeString(status.name(), buf);
            return;
        }
        
        GameSettings settings = message.getGameSettings();

        ByteBufUtilities.writeString(status.name(), buf);
        ByteBufUtilities.writeString(message.getRoomName(), buf);
        buf.writeByte(message.getCapacity());
        buf.writeByte(settings.getMapType().getId());
        buf.writeByte(settings.getBallType().getId());
        buf.writeByte(settings.getDuration().getId());
    }

    @Override
    protected JoinSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String roomName = ByteBufUtilities.readString(buf);
        if (roomName == null) {
            return null;
        }

        String password = ByteBufUtilities.readString(buf);
        if (password == null) {
            return null;
        }

        return new JoinSessionMessage(roomName, password);
    }

}
