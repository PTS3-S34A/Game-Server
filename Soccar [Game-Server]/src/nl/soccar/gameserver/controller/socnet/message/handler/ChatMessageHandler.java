package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.gameserver.controller.socnet.message.ChatMessage;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.session.SessionWrapper;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * @author PTS34A
 */
public final class ChatMessageHandler extends MessageHandler<ChatMessage> {

    @Override
    protected void handle(Connection connection, ChatMessage message) throws Exception {
        String text = message.getMessage();
        if (text == null || text.trim().isEmpty() || text.length() > 75) {
            return;
        }

        GameServer server = GameServer.getInstance();
        PlayerWrapper player = server.getPlayer(connection);
        if (player == null) {
            return;
        }

        SessionWrapper session = player.getCurrentSession();
        if (session == null) {
            return;
        }

        session.getRoom().sendChatMessage(player, message.getMessage());
    }

    @Override
    protected void encode(Connection connection, ChatMessage message, ByteBuf buf) throws Exception {
        buf.writeByte(message.getPlayerId());
        buf.writeByte(message.getPrivilege().getId());
        ByteBufUtilities.writeString(message.getMessage(), buf);
    }

    @Override
    protected ChatMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String message = ByteBufUtilities.readString(buf);
        if (message == null) {
            return null;
        }

        GameServer server = GameServer.getInstance();
        PlayerWrapper player = server.getPlayer(connection);
        if (player == null) {
            connection.close();
            return null;
        }

        return new ChatMessage(player.getPlayerId(), player.getPrivilege(), message);
    }

}
