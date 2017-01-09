package nl.soccar.gameserver.model.session;

import nl.soccar.gameserver.controller.socnet.message.*;
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
import nl.soccar.physics.models.ObstaclePhysics;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import nl.soccar.gameserver.controller.rmi.GameServerRmiController;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.library.Event;
import nl.soccar.library.Player;
import nl.soccar.library.enumeration.EventType;
import nl.soccar.library.enumeration.Privilege;

/**
 * @author PTS34A
 */
public final class GameWrapper {

    private final List<PlayerWrapper> playersReady = new ArrayList<>();

    private final SessionWrapper session;
    private final Game game;

    private final GameEngine engine;
    private Timer timer;

    public GameWrapper(SessionWrapper session, Game game) {
        this.session = session;
        this.game = game;

        engine = new GameEngine(game);
    }

    public void requestStart() {
        initializeWorldObjects();
        sendGameInformation();
    }

    private void start() {
        engine.start();
        game.setPaused(false);

        timer = new Timer();
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

        engine.stop();

        saveStatistics();

        playersReady.clear();
    }

    public void sendWorldObjects(PlayerWrapper player) {
        sendCars(player);
        sendObstacles(player);

        player.getConnection().send(new ChangePlayerStatusMessage(ChangePlayerStatusMessage.Status.READY_TO_PLAY));
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

    public void setPlayerReady(PlayerWrapper player) {
        playersReady.add(player);

        if (playersReady.size() >= session.getRoom().getOccupation()) {
            start();

            PlayerChangedGameStatusMessage message = new PlayerChangedGameStatusMessage(PlayerChangedGameStatusMessage.Status.GAME_RUNNING);
            playersReady.stream()
                    .map(PlayerWrapper::getConnection)
                    .forEach(c -> c.send(message));
        }
    }

    private void saveStatistics() {
        saveGoalsAssists();
        saveGamesWonEvenLost();
    }

    private void saveGoalsAssists() {
        java.util.Map<String, Integer> allGoals = new HashMap<>();
        java.util.Map<String, Integer> allAssists = new HashMap<>();

        game.getEvents().forEach(e -> {
            String player = e.getPlayer().getUsername();
            EventType type = e.getType();
            if (type == EventType.GOAL_BLUE || type == EventType.GOAL_RED) {
                int goals = allGoals.getOrDefault(player, 0);
                allGoals.put(player, ++goals);
            } else if (type == EventType.ASSIST) {
                int assists = allAssists.getOrDefault(player, 0);
                allAssists.put(player, ++assists);
            }
        });

        GameServerRmiController controller = GameServer.getInstance().getRmiControler();
        for (PlayerWrapper p : session.getRoom().getPlayers()) {
            String username = p.getUsername();

            Integer goals = allGoals.get(username);
            if (goals != null && goals > 0) {
                controller.addGoals(username, goals);
            }

            Integer assists = allAssists.get(username);
            if (assists != null && assists > 0) {
                controller.addAssists(username, assists);
            }
        }
    }

    private void saveGamesWonEvenLost() {
        int goalsBlue = 0;
        int goalsRed = 0;

        for (Event e : game.getEvents()) {
            EventType type = e.getType();
            if (type == EventType.GOAL_BLUE) {
                goalsBlue++;
            } else if (type == EventType.GOAL_RED) {
                goalsRed++;
            }
        }

        RoomWrapper room = session.getRoom();
        List<Player> teamBlue = room.getTeamBlue().getPlayers().stream()
                .filter(p -> p.getPrivilege() != Privilege.GUEST)
                .collect(Collectors.toList());
        List<Player> teamRed = room.getTeamRed().getPlayers().stream()
                .filter(p -> p.getPrivilege() != Privilege.GUEST)
                .collect(Collectors.toList());

        GameServerRmiController controller = GameServer.getInstance().getRmiControler();
        if (goalsBlue > goalsRed) {
            teamBlue.stream()
                    .map(Player::getUsername)
                    .forEach(controller::incrementGamesWon);

            teamRed.stream()
                    .map(Player::getUsername)
                    .forEach(controller::incrementGamesLost);
        } else if (goalsRed > goalsBlue) {
            teamBlue.stream()
                    .map(Player::getUsername)
                    .forEach(controller::incrementGamesLost);

            teamRed.stream()
                    .map(Player::getUsername)
                    .forEach(controller::incrementGamesWon);
        } else {
            teamBlue.stream()
                    .map(Player::getUsername)
                    .forEach(controller::incrementGamesPlayed);

            teamRed.stream()
                    .map(Player::getUsername)
                    .forEach(controller::incrementGamesPlayed);
        }
    }

    private void initializeWorldObjects() {
        RoomWrapper room = session.getRoom();
        Map map = game.getMap();

        MapUtilities.addWalls(engine, map);
        CarUtilities.addCars(this, room.getTeamBlue(), room.getTeamRed());

        BallPhysics ball = new BallPhysics(session.getGame().getMap().getBall(), engine.getWorld());
        engine.addWorldObject(ball);
    }

    private void sendGameInformation() {
        PlayerChangedGameStatusMessage message = new PlayerChangedGameStatusMessage(PlayerChangedGameStatusMessage.Status.GAME_PAUSED);
        session.getRoom().getPlayers().stream()
                .map(PlayerWrapper::getConnection)
                .forEach(c -> c.send(message));
    }

    private void sendCars(PlayerWrapper player) {
        List<Car> cars = session.getRoom().getPlayers().stream()
                .map(PlayerWrapper::unwrap)
                .map(engine::getCarFromPlayer)
                .map(CarPhysics::getCar)
                .collect(Collectors.toList());

        Connection connection = player.getConnection();
        cars.stream()
                .map(c -> new SpawnCarMessage(c.getPlayer().getPlayerId(), c.getX(), c.getY(), c.getDegree()))
                .forEach(connection::send);
    }

    private void sendObstacles(PlayerWrapper player) {
        List<ObstaclePhysics> cars = engine.getWorldObjects().stream()
                .filter(o -> o instanceof ObstaclePhysics)
                .map(o -> (ObstaclePhysics) o)
                .collect(Collectors.toList());

        Connection connection = player.getConnection();
        cars.stream()
                .map(ObstaclePhysics::getObstacle)
                .map(o -> new SpawnObstacleMessage(o.getX(), o.getY(), o.getDegree(), o.getWidth(), o.getHeight(), o.getObstacleType()))
                .forEach(connection::send);
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
