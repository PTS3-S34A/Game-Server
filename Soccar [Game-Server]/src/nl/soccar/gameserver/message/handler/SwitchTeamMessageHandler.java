package nl.soccar.gameserver.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.SessionController;
import nl.soccar.gameserver.message.SwitchTeamMessage;
import nl.soccar.library.Player;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 *
 * @author PTS34A
 */
public final class SwitchTeamMessageHandler extends MessageHandler<SwitchTeamMessage> {

    @Override
    protected void handle(Connection connection, SwitchTeamMessage message) throws Exception {
        Player player = connection.getPlayer();
        if (player == null) {
            return;
        }
        
        SessionController.getInstance().switchTeamFromPlayer(player);
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
