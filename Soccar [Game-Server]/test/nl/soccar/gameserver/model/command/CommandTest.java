package nl.soccar.gameserver.model.command;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test that tests the nl.soccar.gameserver.model.command.Command class.
 *
 * @author PTS34A
 */
public class CommandTest {

    // Declaration of test object.
    private Command command;

    /**
     * Instantiation of test objects.
     */
    @Before
    public void setUp() {
        command = new Command("mute", new String[]{"-t", "-n"});
    }

    /**
     * Tests the getName Method.
     * 
     */
    @Test
    public void getNameTest() {
        assertEquals("mute", command.getName());
    }
    
    /**
     * Tests the getArguments Method.
     * 
     */
    @Test
    public void getArgumentsTest() {
        assertEquals(2, command.getArguments().length);
        assertEquals("-t", command.getArguments()[0]);
        assertEquals("-n", command.getArguments()[1]);
    }

}
