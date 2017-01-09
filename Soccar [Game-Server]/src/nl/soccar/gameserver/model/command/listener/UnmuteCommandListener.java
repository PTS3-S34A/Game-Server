package nl.soccar.gameserver.model.command.listener;

import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.command.Command;
import nl.soccar.gameserver.model.command.CommandListener;
import nl.soccar.gameserver.model.session.RoomWrapper;
import nl.soccar.library.enumeration.Privilege;

/**
 * Listener for the Unmute command listener.
 * 
 * @author Lesley
 */
public final class UnmuteCommandListener extends CommandListener {

    /**
     * Intializes the UnmuteCommandListener class.
     */
    public UnmuteCommandListener() {
        super(Privilege.GUEST, true);
    }

    @Override
    public void execute(PlayerWrapper player, RoomWrapper room, Command command) {
        String otherName = String.join(" ", command.getArguments());
        if (otherName.trim().isEmpty()) {
            return;
        }

        room.unmutePlayer(player, otherName);
    }

}
