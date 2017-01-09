package nl.soccar.gameserver.model.command;

/**
 * A Command for the Chat in the room.
 * 
 * @author PTS34A
 */
public final class Command {

    private final String name;
    private final String[] arguments;

    /**
     * Intializes the Command class.
     * 
     * @param name The Command name, not null.
     * @param arguments The arguments for the command, not null.
     */
    public Command(String name, String[] arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    /**
     * Gets the command name.
     * 
     * @return String The command name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the arguments of the command.
     * 
     * @return String[] The arguments of the command.
     */
    public String[] getArguments() {
        return arguments;
    }

}
