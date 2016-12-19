package nl.soccar.gameserver.model.command.listener;

import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.command.Command;
import nl.soccar.gameserver.model.command.CommandListener;
import nl.soccar.gameserver.model.session.RoomWrapper;
import nl.soccar.library.enumeration.Privilege;

/**
 * @author Lesley
 */
public final class MuteCommandListener extends CommandListener {

    public MuteCommandListener() {
        super(Privilege.GUEST, true);
    }

    @Override
    public void execute(PlayerWrapper player, RoomWrapper room, Command command) {
        String otherName = String.join(" ", command.getArguments());
        if (otherName.trim().isEmpty()) {
            return;
        }

        room.mutePlayer(player, otherName);
    }

}
