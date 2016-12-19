package nl.soccar.gameserver.model.command.listener;

import nl.soccar.gameserver.controller.socnet.message.ChatMessage;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.command.Command;
import nl.soccar.gameserver.model.command.CommandListener;
import nl.soccar.gameserver.model.session.RoomWrapper;
import nl.soccar.library.enumeration.Privilege;

/**
 * @author Lesley
 */
public final class BroadcastCommandListener extends CommandListener {

    public BroadcastCommandListener() {
        super(Privilege.ADMINISTRATOR, false);
    }

    @Override
    public void execute(PlayerWrapper player, RoomWrapper room, Command command) {
        String message = String.join(" ", command.getArguments());
        if (message.trim().isEmpty()) {
            return;
        }

        ChatMessage m = new ChatMessage(-1, Privilege.ADMINISTRATOR, message);
        room.getPlayers().stream()
                .map(PlayerWrapper::getConnection)
                .forEach(c -> c.send(m));
    }

}
