package nl.soccar.gameserver.model.command;

import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.command.listener.BroadcastCommandListener;
import nl.soccar.gameserver.model.command.listener.MuteCommandListener;
import nl.soccar.gameserver.model.command.listener.SetHostCommandListener;
import nl.soccar.gameserver.model.command.listener.UnmuteCommandListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Dispatcher for the Commands in the Chat.
 * 
 * @author PTS34A
 */
public final class CommandDispatcher {

    private static final Map<String, CommandListener> listeners = new HashMap<>();

    static {
        register("mute", new MuteCommandListener());
        register("unmute", new UnmuteCommandListener());
        register("broadcast", new BroadcastCommandListener());
        register("sethost", new SetHostCommandListener());
    }

    private CommandDispatcher() {
    }

    /**
     * Dispatchs the Command and executes it.
     * 
     * @param player The given player. not null.
     * @param command The given Command, not null.
     */
    public static void dispatch(PlayerWrapper player, Command command) {
        CommandListener listener = listeners.get(command.getName().toLowerCase());
        if (listener != null) {
            listener.executePrivileged(player, command);
        }
    }

    /**
     * Registers the command to the given listener.
     * 
     * @param command The given command, not null.
     * @param listener The given Listener, not null.
     */
    public static void register(String command, CommandListener listener) {
        listeners.put(command.toLowerCase(), listener);
    }

    /**
     * Deregisters the command.
     * 
     * @param command The given command, not null.
     */
    public static void deregister(String command) {
        listeners.remove(command);
    }

}
