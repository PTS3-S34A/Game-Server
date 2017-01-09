package nl.soccar.gameserver.model.command;

import nl.soccar.gameserver.controller.socnet.message.ChatMessage;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.session.RoomWrapper;
import nl.soccar.gameserver.model.session.SessionWrapper;
import nl.soccar.library.enumeration.Privilege;

/**
 * Listener for listening to Commands.
 *
 * @author PTS34A
 */
public abstract class CommandListener {

    private final Privilege minimumPrivilege;
    private final boolean hostOnly;

    /**
     * Initializes the CommandListener class.
     *
     * @param minimumPrivilege The minimum privilege to execute commands, not
     * null.
     */
    public CommandListener(Privilege minimumPrivilege) {
        this(minimumPrivilege, false);
    }

    /**
     * Intializes the CommandListener class.
     *
     * @param minimumPrivilege The minimum privilege to execute commands, not
     * null.
     * @param hostOnly True if the command may only be executed on the host
     * itself, false if the commands need to be executed on the whole Room.
     */
    public CommandListener(Privilege minimumPrivilege, boolean hostOnly) {
        this.minimumPrivilege = minimumPrivilege;
        this.hostOnly = hostOnly;
    }

    /**
     * Executes the command in the Chat.
     *
     * @param player The player that is executing the command, not null.
     * @param room The Room where the command needs to be executed in, not null.
     * @param command The command that needs to be executed, not null.
     */
    public abstract void execute(PlayerWrapper player, RoomWrapper room, Command command);

    /**
     * Executes the command in the Chat with the set Privileges.
     * 
     * @param player The player that is executing the command, not null.
     * @param command The command that needs to be executed, not null.
     */
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
