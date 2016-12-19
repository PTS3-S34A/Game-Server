package nl.soccar.gameserver.model.session;

import nl.soccar.library.Session;

/**
 * @author Lesley
 */
public final class SessionWrapper {

    private final Session session;
    private final RoomWrapper room;
    private final GameWrapper game;

    public SessionWrapper(Session session) {
        this.session = session;

        room = new RoomWrapper(this, session.getRoom());
        game = new GameWrapper(this, session.getGame());
    }

    public void destroy() {
        game.stop();
    }

    public RoomWrapper getRoom() {
        return room;
    }

    public GameWrapper getGame() {
        return game;
    }

    public Session unwrap() {
        return session;
    }
}
