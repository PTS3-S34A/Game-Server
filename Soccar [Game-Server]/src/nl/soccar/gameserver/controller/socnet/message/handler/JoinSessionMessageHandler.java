package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.gameserver.controller.socnet.message.JoinSessionMessage;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.session.RoomWrapper;
import nl.soccar.gameserver.model.session.SessionController;
import nl.soccar.gameserver.model.session.SessionWrapper;
import nl.soccar.library.GameSettings;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * @author PTS34A
 */
public final class JoinSessionMessageHandler extends MessageHandler<JoinSessionMessage> {

    @Override
    protected void handle(Connection connection, JoinSessionMessage message) throws Exception {
        GameServer server = GameServer.getInstance();
        PlayerWrapper player = server.getPlayer(connection);
        if (player == null) {
            return;
        }

        SessionController controller = server.getSessionController();
        SessionWrapper session = controller.getSession(message.getRoomName());
        if (session == null) {
            connection.send(new JoinSessionMessage(JoinSessionMessage.Status.SESSION_NON_EXISTENT));
            return;
        }

        RoomWrapper room = session.getRoom();
        room.join(player, message.getPassword());
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
