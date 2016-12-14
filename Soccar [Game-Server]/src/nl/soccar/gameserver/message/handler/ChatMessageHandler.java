package nl.soccar.gameserver.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.gameserver.message.ChatMessage;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.socnet.Node;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

public final class ChatMessageHandler extends MessageHandler<ChatMessage> {

    @Override
    protected void handle(Connection connection, ChatMessage message) throws Exception {
        String text = message.getMessage();
        if (text == null || text.trim().isEmpty() || text.length() > 75) {
            return;
        }

        Node server = connection.getNode();
        if (server == null) {
            return;
        }

        Player player = connection.getPlayer();
        if (player == null) {
            return;
        }

        Session session = player.getCurrentSession();
        if (session == null) {
            return;
        }

        session.getRoom().getAllPlayers().stream().map(server::getConnectionFromPlayer).forEach(c -> c.send(message));
    }

    @Override
    protected void encode(Connection connection, ChatMessage message, ByteBuf buf) throws Exception {
        Player player = message.getPlayer();

        ByteBufUtilities.writeString(player.getUsername(), buf);
        buf.writeByte(player.getPrivilege().getId());
        ByteBufUtilities.writeString(message.getMessage(), buf);
    }

    @Override
    protected ChatMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String message = ByteBufUtilities.readString(buf);
        if (message == null) {
            return null;
        }

        Player player = connection.getPlayer();
        return new ChatMessage(player, message);
    }

}
