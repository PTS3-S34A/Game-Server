package nl.soccar.gameserver.model.session;

import nl.soccar.library.Session;

/**
 * Wrapper of the Session class.
 *
 * @author PTS34A
 */
public final class SessionWrapper {

    private final Session session;
    private final RoomWrapper room;
    private final GameWrapper game;

    /**
     * Intializes the SessionWrapper class.
     *
     * @param session The session, not null.
     */
    public SessionWrapper(Session session) {
        this.session = session;

        room = new RoomWrapper(this, session.getRoom());
        game = new GameWrapper(this, session.getGame());
    }

    /**
     * Destroys the Session.
     */
    public void destroy() {
        game.stop();
    }

    /**
     * Gets the Room.
     *
     * @return RoomWrapper The Room of this Session.
     */
    public RoomWrapper getRoom() {
        return room;
    }

    /**
     * Gets the Game.
     *
     * @return GameWrapper The Game of this Session.
     */
    public GameWrapper getGame() {
        return game;
    }

    /**
     * Unwraps this Session.
     *
     * @return Session This Session.
     */
    public Session unwrap() {
        return session;
    }
}
