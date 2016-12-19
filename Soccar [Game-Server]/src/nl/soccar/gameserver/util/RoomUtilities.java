package nl.soccar.gameserver.util;

import nl.soccar.gameserver.model.session.RoomWrapper;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author PTS34A
 */
public final class RoomUtilities {

    private static final Random RANDOM = new Random();

    private RoomUtilities() {
    }

    public static final int getNextPlayerId(RoomWrapper room) {
        final AtomicInteger id = new AtomicInteger(RANDOM.nextInt(Byte.MAX_VALUE));
        while (room.getPlayers().stream().anyMatch(p -> p.getPlayerId() == id.get())) {
            id.set(RANDOM.nextInt(Byte.MAX_VALUE));
        }

        return id.get();
    }

}
