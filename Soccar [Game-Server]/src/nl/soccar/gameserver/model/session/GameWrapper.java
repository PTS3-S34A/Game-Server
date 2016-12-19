package nl.soccar.gameserver.model.session;

import nl.soccar.gameserver.controller.socnet.message.BallSyncMessage;
import nl.soccar.gameserver.controller.socnet.message.MovePlayerMessage;
import nl.soccar.gameserver.controller.socnet.message.PlayerStartedGameMessage;
import nl.soccar.gameserver.controller.socnet.message.PlayerSyncMessage;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.util.CarUtilities;
import nl.soccar.gameserver.util.MapUtilities;
import nl.soccar.library.Car;
import nl.soccar.library.Game;
import nl.soccar.library.GameSettings;
import nl.soccar.library.Map;
import nl.soccar.library.enumeration.GameStatus;
import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.ThrottleAction;
import nl.soccar.physics.GameEngine;
import nl.soccar.physics.models.BallPhysics;
import nl.soccar.physics.models.CarPhysics;
import nl.soccar.socnet.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Lesley
 */
public final class GameWrapper {

    private final SessionWrapper session;
    private final Game game;

    private final GameEngine engine;
    private final Timer timer;

    public GameWrapper(SessionWrapper session, Game game) {
        this.session = session;
        this.game = game;

        engine = new GameEngine(game);
        timer = new Timer();
    }

    public void start() {
        initializeWorldObjects();
        sendGameInformation();

        engine.start();
        game.start();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (game.getStatus() == GameStatus.STOPPED) {
                    stop();
                    return;
                }

                List<Message> messages = getSynchronisationMessages();
                session.getRoom().getPlayers().stream()
                        .map(PlayerWrapper::getConnection)
                        .forEach(c -> messages.forEach(c::send));
            }
        }, 0, 75);
    }

    public void stop() {
        timer.cancel();

        game.stop();
        engine.stop();
    }

    public void movePlayer(PlayerWrapper player, SteerAction steerAction, HandbrakeAction handbrakeAction, ThrottleAction throttleAction) {
        Map map = game.getMap();

        if (game.getStatus() == GameStatus.RUNNING) {
            Car car = map.getCarFromPlayer(player.unwrap());

            car.setHandbrakeAction(handbrakeAction);
            car.setSteerAction(steerAction);
            car.setThrottleAction(throttleAction);
        }

        MovePlayerMessage message = new MovePlayerMessage(player.getPlayerId(), steerAction, handbrakeAction, throttleAction);
        session.getRoom().getPlayers().stream()
                .filter(p -> !p.equals(player))
                .map(PlayerWrapper::getConnection)
                .forEach(c -> c.send(message));
    }

    private void initializeWorldObjects() {
        RoomWrapper room = session.getRoom();
        Map map = game.getMap();

        MapUtilities.addWalls(engine, map);
        CarUtilities.addCars(engine, map, room.getTeamBlue(), room.getTeamRed());

        BallPhysics ball = new BallPhysics(session.getGame().getMap().getBall(), engine.getWorld());
        engine.addWorldObject(ball);
    }

    private void sendGameInformation() {
        RoomWrapper room = session.getRoom();

        PlayerStartedGameMessage message = new PlayerStartedGameMessage();
        room.getPlayers().stream()
                .map(PlayerWrapper::getConnection)
                .forEach(c -> c.send(message));
    }

    private List<Message> getSynchronisationMessages() {
        List<Message> messages = new ArrayList<>();

        RoomWrapper room = session.getRoom();
        List<PlayerWrapper> players = room.getPlayers();

        for (PlayerWrapper p : players) {
            CarPhysics car = engine.getCarFromPlayer(p.unwrap());

            messages.add(new PlayerSyncMessage(p.getPlayerId(), car.getX(), car.getY(), car.getDegree(), car.getLinearVelocityX(), car.getLinearVelocityY(), car.getAngularVelocity()));
        }

        BallPhysics ball = engine.getBall();
        messages.add(new BallSyncMessage(ball.getX(), ball.getY(), ball.getLinearVelocityX(), ball.getLinearVelocityY(), ball.getAngularVelocity()));

        return messages;
    }

    public GameEngine getGameEngine() {
        return engine;
    }

    public GameSettings getGameSettings() {
        return game.getGameSettings();
    }

    public Map getMap() {
        return game.getMap();
    }
}
