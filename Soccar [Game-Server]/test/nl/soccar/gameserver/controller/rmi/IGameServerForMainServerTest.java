package nl.soccar.gameserver.controller.rmi;

import java.rmi.RemoteException;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.library.enumeration.MapType;
import nl.soccar.rmi.interfaces.IGameServerForMainServer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test that tests the
 * nl.soccar.gameserver.controller.rmi.GameServerForMainServer class.
 *
 * @author PTS34A
 */
public class IGameServerForMainServerTest {

    // Declaration of test object.
    private IGameServerForMainServer gameServerForMainServer;

    /**
     * Instantiation of test objects.
     */
    @Before
    public void setUp() {
        gameServerForMainServer = new MockGameServerForMainServer();
    }

    /**
     * Tests the createSession Method.
     *
     * @throws RemoteException Thrown when a communication error occurs during
     * the remote call of this method.
     */
    @Test
    public void createSessionTest() throws RemoteException {
        assertTrue(gameServerForMainServer.createSession("sessionName", "password", "hostName", 0, Duration.MINUTES_3, MapType.ICE, BallType.FOOTBALL));
    }

    /**
     * Tests the getAvailableMemory Method.
     *
     * @throws RemoteException Thrown when a communication error occurs during
     * the remote call of this method.
     */
    @Test
    public void getAvailableMemoryTest() throws RemoteException {
        assertEquals(1, gameServerForMainServer.getAvailableMemory());
    }

    /**
     * Tests the ping Method.
     *
     * @throws RemoteException Thrown when a communication error occurs during
     * the remote call of this method.
     */
    @Test
    public void pingTest() throws RemoteException {
        assertEquals(1, gameServerForMainServer.ping(0));
    }
}
