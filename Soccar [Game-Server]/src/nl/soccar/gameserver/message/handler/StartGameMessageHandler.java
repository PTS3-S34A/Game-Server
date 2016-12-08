package nl.soccar.gameserver.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gameserver.SessionController;
import nl.soccar.gameserver.message.StartGameMessage;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 *
 * @author PTS34A
 */
public final class StartGameMessageHandler extends MessageHandler<StartGameMessage> {

    @Override
    protected void handle(Connection connection, StartGameMessage message) throws Exception {
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
        SessionController.getInstance().startGame(session);
    }

    @Override
    protected void encode(Connection connection, StartGameMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding is not supported on Server."); 
    }

    @Override
    protected StartGameMessage decode(Connection connection, ByteBuf buf) throws Exception {
        return new StartGameMessage();
    }
    
}
