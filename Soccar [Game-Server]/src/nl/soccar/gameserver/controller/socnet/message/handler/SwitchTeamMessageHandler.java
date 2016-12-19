package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.SwitchTeamMessage;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.session.RoomWrapper;
import nl.soccar.gameserver.model.session.SessionWrapper;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * @author PTS34A
 */
public final class SwitchTeamMessageHandler extends MessageHandler<SwitchTeamMessage> {

    @Override
    protected void handle(Connection connection, SwitchTeamMessage message) throws Exception {
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
        room.switchTeam(player);
    }

    @Override
    protected void encode(Connection connection, SwitchTeamMessage message, ByteBuf buf) throws Exception {
        buf.writeByte(message.getPlayerId());
        buf.writeByte(message.getTeamColour().getId());
    }

    @Override
    protected SwitchTeamMessage decode(Connection connection, ByteBuf buf) throws Exception {
        return new SwitchTeamMessage();
    }

}
