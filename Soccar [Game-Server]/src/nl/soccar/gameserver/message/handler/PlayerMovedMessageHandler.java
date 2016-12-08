package nl.soccar.gameserver.message.handler;

import io.netty.buffer.ByteBuf;
import java.util.logging.Logger;
import nl.soccar.gameserver.SessionController;
import nl.soccar.gameserver.message.PlayerMovedMessage;
import nl.soccar.library.Player;
import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.ThrottleAction;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 *
 * @author PTS34A
 */
public final class PlayerMovedMessageHandler extends MessageHandler<PlayerMovedMessage> {

    @Override
    protected void handle(Connection connection, PlayerMovedMessage message) throws Exception {
        Player player = connection.getPlayer();
        if (player == null) {
            connection.close();
            return;
        }

        SessionController.getInstance().movePlayer(player, message.getSteerAction(), message.getHandbrakAction(), message.getThrottleAction());
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
