package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.ChangePlayerStatusMessage;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.session.SessionWrapper;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * Handler for the ChangePlayerStatusMessage class.
 * 
 * @author PTS34A
 */
public final class ChangePlayerStatusMessageHandler extends MessageHandler<ChangePlayerStatusMessage> {

    @Override
    protected void handle(Connection connection, ChangePlayerStatusMessage message) throws Exception {
        GameServer server = GameServer.getInstance();
        PlayerWrapper player = server.getPlayer(connection);
        if (player == null) {
            return;
        }

        SessionWrapper session = player.getCurrentSession();
        if (session == null) {
            return;
        }

        ChangePlayerStatusMessage.Status status = message.getStatus();

        System.out.println(status);

        if (status == ChangePlayerStatusMessage.Status.READY_TO_PLAY) {
            session.getGame().setPlayerReady(player);
        } else if (status == ChangePlayerStatusMessage.Status.REQUEST_WORLD_OBJECTS) {
            session.getGame().sendWorldObjects(player);
        }
    }

    @Override
    protected void encode(Connection connection, ChangePlayerStatusMessage message, ByteBuf buf) throws Exception {
        buf.writeByte(message.getStatus().ordinal());
    }

    @Override
    protected ChangePlayerStatusMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 1) {
            buf.resetReaderIndex();
            return null;
        }

        return new ChangePlayerStatusMessage(ChangePlayerStatusMessage.Status.values()[buf.readByte()]);
    }

}
