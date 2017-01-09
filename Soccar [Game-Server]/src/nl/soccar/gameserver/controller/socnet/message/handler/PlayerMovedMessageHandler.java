package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.controller.socnet.message.PlayerMovedMessage;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.session.GameWrapper;
import nl.soccar.gameserver.model.session.SessionWrapper;
import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.ThrottleAction;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * Handler for the PlayerMovedMessage class.
 * 
 * @author PTS34A
 */
public final class PlayerMovedMessageHandler extends MessageHandler<PlayerMovedMessage> {

    @Override
    protected void handle(Connection connection, PlayerMovedMessage message) throws Exception {
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
        game.movePlayer(player, message.getSteerAction(), message.gethandbrakaction(), message.getThrottleAction());
    }

    @Override
    protected void encode(Connection connection, PlayerMovedMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding is not supported for Client.");
    }

    @Override
    protected PlayerMovedMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 3) {
            buf.resetReaderIndex();
            return null;
        }

        SteerAction steerAction = SteerAction.parse(buf.readByte());
        HandbrakeAction handbrakeAction = HandbrakeAction.parse(buf.readByte());
        ThrottleAction throttleAction = ThrottleAction.parse(buf.readByte());

        return new PlayerMovedMessage(steerAction, handbrakeAction, throttleAction);
    }

}
