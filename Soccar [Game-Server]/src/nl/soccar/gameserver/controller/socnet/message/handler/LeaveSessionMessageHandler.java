package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.LeaveSessionMessage;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.session.RoomWrapper;
import nl.soccar.gameserver.model.session.SessionWrapper;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * Handler for the LeaveSessionMessage class.
 * 
 * @author PTS34A
 */
public class LeaveSessionMessageHandler extends MessageHandler<LeaveSessionMessage> {

    @Override
    protected void handle(Connection connection, LeaveSessionMessage message) throws Exception {
        GameServer server = GameServer.getInstance();
        PlayerWrapper player = server.getPlayer(connection);
        if (player == null) {
            return;
        }

        SessionWrapper session = player.getCurrentSession();
        if (session == null) {
            return;
        }

        RoomWrapper room = session.getRoom();
        room.leave(player);
    }

    @Override
    protected void encode(Connection connection, LeaveSessionMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding is not supported by the Game Server.");
    }

    @Override
    protected LeaveSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        return new LeaveSessionMessage();
    }

}
