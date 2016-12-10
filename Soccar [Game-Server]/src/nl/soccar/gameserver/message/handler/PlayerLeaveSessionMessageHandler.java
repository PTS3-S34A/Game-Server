package nl.soccar.gameserver.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.gameserver.SessionController;
import nl.soccar.gameserver.message.PlayerLeaveSessionMessage;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 *
 * @author PTS34A
 */
public class PlayerLeaveSessionMessageHandler extends MessageHandler<PlayerLeaveSessionMessage> {
    
    @Override
    protected void handle(Connection connection, PlayerLeaveSessionMessage message) throws Exception {
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
        
        if (!SessionController.getInstance().leaveSession(player, message.getTeamColour(), session)) {
            connection.close();
        }
    }

    @Override
    protected void encode(Connection connection, PlayerLeaveSessionMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding is not supported by the Game Server."); 
    }

    @Override
    protected PlayerLeaveSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String username = ByteBufUtilities.readString(buf);
        if (username == null) {
            return null;
        }
        
        if (buf.readableBytes() < 1) {
            buf.resetReaderIndex();
            return null;
        }
        
        TeamColour colour = TeamColour.parse(buf.readByte());
        
        return new PlayerLeaveSessionMessage(username, colour);
    }
    
}
