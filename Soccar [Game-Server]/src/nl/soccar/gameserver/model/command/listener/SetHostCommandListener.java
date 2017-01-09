package nl.soccar.gameserver.model.command.listener;

import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.command.Command;
import nl.soccar.gameserver.model.command.CommandListener;
import nl.soccar.gameserver.model.session.RoomWrapper;
import nl.soccar.library.enumeration.Privilege;

/**
 * Listener for set-host commands.
 *
 * @author Lesley
 */
public final class SetHostCommandListener extends CommandListener {

    /**
     * Intializes the SetHostCommandListener class.
     */
    public SetHostCommandListener() {
        super(Privilege.GUEST, true);
    }

    @Override
    public void execute(PlayerWrapper player, RoomWrapper room, Command command) {
        String otherName = String.join(" ", command.getArguments());
        if (otherName.trim().isEmpty()) {
            return;
        }

        room.changeHost(player, otherName);
    }

}
