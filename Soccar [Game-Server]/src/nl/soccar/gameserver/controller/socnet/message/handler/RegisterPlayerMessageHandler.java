package nl.soccar.gameserver.controller.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.gameserver.controller.socnet.message.RegisterPlayerMessage;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.library.Player;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 * @author PTS34A
 */
public final class RegisterPlayerMessageHandler extends MessageHandler<RegisterPlayerMessage> {

    @Override
    protected void handle(Connection connection, RegisterPlayerMessage message) throws Exception {
        String username = message.getUsername();
        Privilege privilege = Privilege.GUEST;

        Player player = new Player(username, privilege, message.getCarType());
        PlayerWrapper wrapper = new PlayerWrapper(connection, player);

        GameServer.getInstance().registerPlayer(connection, wrapper);
    }

    @Override
    protected void encode(Connection connection, RegisterPlayerMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding not supported for Server.");
    }

    @Override
    protected RegisterPlayerMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String username = ByteBufUtilities.readString(buf);
        if (username == null) {
            return null;
        }

        if (buf.readableBytes() < 1) {
            buf.resetReaderIndex();
            return null;
        }

        CarType type = CarType.parse(buf.readByte());
        return new RegisterPlayerMessage(username, type);
    }

}
