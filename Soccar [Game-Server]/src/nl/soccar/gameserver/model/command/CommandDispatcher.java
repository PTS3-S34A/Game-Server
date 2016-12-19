package nl.soccar.gameserver.model.command;

import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.model.command.listener.BroadcastCommandListener;
import nl.soccar.gameserver.model.command.listener.MuteCommandListener;
import nl.soccar.gameserver.model.command.listener.UnmuteCommandListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author PTS34A
 */
public final class CommandDispatcher {

    private static final Map<String, CommandListener> listeners = new HashMap<>();

    static {
        register("mute", new MuteCommandListener());
        register("unmute", new UnmuteCommandListener());
        register("broadcast", new BroadcastCommandListener());
    }

    private CommandDispatcher() {
    }

    public static void dispatch(PlayerWrapper player, Command command) {
        CommandListener listener = listeners.get(command.getName().toLowerCase());
        if (listener != null) {
            listener.executePrivileged(player, command);
        }
    }

    public static void register(String command, CommandListener listener) {
        listeners.put(command.toLowerCase(), listener);
    }

    public static void deregister(String command) {
        listeners.remove(command);
    }

}
