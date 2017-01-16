package nl.soccar.gameserver.model.session;

import nl.soccar.gameserver.model.GameServer;
import nl.soccar.library.Ball;
import nl.soccar.library.Game;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.EventType;
import nl.soccar.physics.GameEngine;
import nl.soccar.physics.listener.GameEventListener;

/**
 * Listens for Events, namely when the ball enters the goal, and acts upon them.
 *
 * @author PTS34A
 */
public final class ServerGameEventListener implements GameEventListener {

    @Override
    public void onBallInGoal(GameEngine engine, Session session, Ball ball, EventType type) {
        Game game = session.getGame();
        Player player = ball.getLastTouched();

        game.setPaused(true);
        engine.resetWorldObjects();

        SessionWrapper wrapper = GameServer.getInstance().getSessionController().getSession(session.getRoom().getName());
        wrapper.getGame().addGameEvent(type, player);

        game.setPaused(false);
    }

}
