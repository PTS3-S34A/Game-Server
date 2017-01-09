package nl.soccar.gameserver.util;

import nl.soccar.gameserver.model.session.RoomWrapper;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Utilities for the Room.
 *
 * @author PTS34A
 */
public final class RoomUtilities {

    private static final Random RANDOM = new Random();

    private RoomUtilities() {
    }

    /**
     * Gets the next player id.
     *
     * @param room The given Room, not null.
     * @return int The next player Id.
     */
    public static final int getNextPlayerId(RoomWrapper room) {
        final AtomicInteger id = new AtomicInteger(RANDOM.nextInt(Byte.MAX_VALUE));
        while (room.getPlayers().stream().anyMatch(p -> p.getPlayerId() == id.get())) {
            id.set(RANDOM.nextInt(Byte.MAX_VALUE));
        }

        return id.get();
    }

}
