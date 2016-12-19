package nl.soccar.gameserver.model.command;

/**
 * @author PTS34A
 */
public final class Command {

    private final String name;
    private final String[] arguments;

    public Command(String name, String[] arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public String[] getArguments() {
        return arguments;
    }

}
