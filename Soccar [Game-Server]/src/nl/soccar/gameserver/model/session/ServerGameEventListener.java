package nl.soccar.gameserver.model.session;

import nl.soccar.gameserver.controller.socnet.message.EventMessage;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.library.*;
import nl.soccar.library.enumeration.EventType;
import nl.soccar.physics.GameEngine;
import nl.soccar.physics.listener.GameEventListener;

import java.time.LocalTime;

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
        game.addEvent(new Event(type, LocalTime.now(), player));

        EventMessage message = new EventMessage(type, player.getPlayerId());
        SessionWrapper wrapper = GameServer.getInstance().getSessionController().getSession(session.getRoom().getName());
        wrapper.getRoom().getPlayers().stream()
                .map(PlayerWrapper::getConnection)
                .forEach(c -> c.send(message));

        game.setPaused(false);
    }

}
