package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.GameStatusMessage;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.session.GameWrapper;
import nl.soccar.gameserver.model.session.SessionWrapper;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * Handler for the GameStatusMessage class.
 *
 * @author PTS34A
 */
public final class GameStatusMessageHandler extends MessageHandler<GameStatusMessage> {

    @Override
    protected void handle(Connection connection, GameStatusMessage message) throws Exception {
        GameServer server = GameServer.getInstance();
        PlayerWrapper player = server.getPlayer(connection);
        if (player == null) {
            return;
        }

        SessionWrapper session = player.getCurrentSession();
        if (session == null) {
            return;
        }

        GameWrapper game = session.getGame();
        connection.send(new GameStatusMessage(game.getGameStatus()));
    }

    @Override
    protected void encode(Connection connection, GameStatusMessage message, ByteBuf buf) throws Exception {
        buf.writeByte(message.getGameStatus().ordinal());
    }

    @Override
    protected GameStatusMessage decode(Connection connection, ByteBuf buf) throws Exception {
        return new GameStatusMessage();
    }

}
