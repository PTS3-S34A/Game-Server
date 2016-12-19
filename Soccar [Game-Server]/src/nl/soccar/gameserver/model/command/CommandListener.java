package nl.soccar.gameserver.model.command;

import nl.soccar.gameserver.controller.socnet.message.ChatMessage;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.session.RoomWrapper;
import nl.soccar.gameserver.model.session.SessionWrapper;
import nl.soccar.library.enumeration.Privilege;

/**
 * @author Lesley
 */
public abstract class CommandListener {

    private final Privilege minimumPrivilege;
    private final boolean hostOnly;

    public CommandListener(Privilege minimumPrivilege) {
        this(minimumPrivilege, false);
    }

    public CommandListener(Privilege minimumPrivilege, boolean hostOnly) {
        this.minimumPrivilege = minimumPrivilege;
        this.hostOnly = hostOnly;
    }

    public abstract void execute(PlayerWrapper player, RoomWrapper room, Command command);

    public final void executePrivileged(PlayerWrapper player, Command command) {
        SessionWrapper session = player.getCurrentSession();
        if (session == null) {
            return;
        }

        RoomWrapper room = session.getRoom();

        Privilege privilege = player.getPrivilege();
        if (privilege != Privilege.ADMINISTRATOR && (privilege.getId() < minimumPrivilege.getId() || (hostOnly && !room.getHost().equals(player)))) {
            sendInsufficientPrivilegeMessage(player);
            return;
        }

        execute(player, room, command);
    }

    private final void sendInsufficientPrivilegeMessage(PlayerWrapper player) {
        player.getConnection().send(new ChatMessage(-1, Privilege.ADMINISTRATOR, "You do not have the required privilege to execute this command."));
    }

}