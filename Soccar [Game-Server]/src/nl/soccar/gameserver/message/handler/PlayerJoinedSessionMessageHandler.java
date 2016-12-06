package nl.soccar.gameserver.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.gameserver.message.PlayerJoinedSessionMessage;
import nl.soccar.library.Player;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * @author PTS34A
 */
public final class PlayerJoinedSessionMessageHandler extends MessageHandler<PlayerJoinedSessionMessage> {

    @Override
    protected void handle(Connection connection, PlayerJoinedSessionMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling not supported for Server.");
    }

    @Override
    protected void encode(Connection connection, PlayerJoinedSessionMessage message, ByteBuf buf) throws Exception {
        Player player = message.getPlayer();

        ByteBufUtilities.writeString(player.getUsername(), buf);
        ByteBufUtilities.writeString(player.getPrivilege().name(), buf);
        ByteBufUtilities.writeString(player.getCarType().name(), buf);
        ByteBufUtilities.writeString(message.getTeam().name(), buf);
    }

    @Override
    protected PlayerJoinedSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding not supported for Server.");
    }

}
