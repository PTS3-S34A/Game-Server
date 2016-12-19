package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.gameserver.controller.socnet.message.PlayerJoinedSessionMessage;
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
        buf.writeByte(message.getPlayerId());
        ByteBufUtilities.writeString(message.getUsername(), buf);
        buf.writeByte(message.getPrivilege().getId());

        buf.writeByte(message.getCarType().getId());
        buf.writeByte(message.getTeam().getId());
    }

    @Override
    protected PlayerJoinedSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding not supported for Server.");
    }

}
