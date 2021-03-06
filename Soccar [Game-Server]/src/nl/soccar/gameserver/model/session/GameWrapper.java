package nl.soccar.gameserver.model.session;

import nl.soccar.gameserver.controller.rmi.GameServerRmiController;
import nl.soccar.gameserver.controller.socnet.message.*;
import nl.soccar.gameserver.model.GameServer;
import nl.soccar.gameserver.model.PlayerWrapper;
import nl.soccar.gameserver.util.CarUtilities;
import nl.soccar.gameserver.util.MapUtilities;
import nl.soccar.library.*;
import nl.soccar.library.Map;
import nl.soccar.library.enumeration.*;
import nl.soccar.physics.GameEngine;
import nl.soccar.physics.models.BallPhysics;
import nl.soccar.physics.models.CarPhysics;
import nl.soccar.physics.models.ObstaclePhysics;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.Message;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Wrapper for the Game class.
 *
 * @author PTS34A
 */
public final class GameWrapper {

    private final List<PlayerWrapper> playersReady = new ArrayList<>();
    private final List<PlayerWrapper> playersIngame = new ArrayList<>();

    private final SessionWrapper session;
    private final Game game;

    private GameEngine engine;
    private Timer timer;

    /**
     * Intializes the GameWrapper class.
     *
     * @param session The session, not null.
     * @param game    The game, not null.
     */
    public GameWrapper(SessionWrapper session, Game game) {
        this.session = session;
        this.game = game;
    }

    /**
     * Starts the game.
     */
    public void requestStart() {
        initializeEngine();
        initializeWorldObjects();
        sendGameInformation();
    }

    private void initializeEngine() {
        engine = new GameEngine(session.unwrap());
        engine.addListener(new ServerGameEventListener());
    }

    private void start() {
        playersReady.forEach(playersIngame::add);
        playersReady.clear();

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

                if (game.getStatus() != GameStatus.RUNNING) {
                    return;
                }

                List<Message> messages = getSynchronisationMessages();
                playersIngame.stream()
                        .map(PlayerWrapper::getConnection)
                        .forEach(c -> messages.forEach(c::send));
            }
        }, 0, 75);

        notifyGameStatus();
    }

    /**
     * Stops the game.
     */
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;

            engine.stop();
            engine = null;

            sendStopGameMessage();
            saveStatistics();
        }

        game.getMap().removeCars();

        playersReady.clear();
        playersIngame.clear();

        notifyGameStatus();
    }

    /**
     * Adds an event to the Game-object. Also notifies all connected players that an Event has actually occurred.
     *
     * @param type   The type of Event that should be added.
     * @param player The player that triggered the Event.
     */
    public void addGameEvent(EventType type, Player player) {
        EventMessage message = new EventMessage(type, player.getPlayerId());
        playersIngame.stream()
                .map(PlayerWrapper::getConnection)
                .forEach(c -> c.send(message));

        game.addEvent(new Event(type, LocalTime.now(), player));
    }

    /**
     * Sends the WorldObjects to the player.
     *
     * @param player The given player, not null.
     */
    public void sendWorldObjects(PlayerWrapper player) {
        sendCars(player);
        sendObstacles(player);
        sendBall(player);

        player.getConnection().send(new ChangePlayerStatusMessage(ChangePlayerStatusMessage.Status.READY_TO_PLAY));
    }

    /**
     * Moves the position of the given player and sends this to all the other
     * players.
     *
     * @param player          The given player, not null.
     * @param steerAction     The steer action of the player, not null.
     * @param handbrakeAction The handbrake action of the player, not null.
     * @param throttleAction  The throttole action of the player, not null.
     */
    public void movePlayer(PlayerWrapper player, SteerAction steerAction, HandbrakeAction handbrakeAction, ThrottleAction throttleAction) {
        Map map = game.getMap();

        if (game.getStatus() == GameStatus.RUNNING) {
            Car car = map.getCarFromPlayer(player.unwrap());

            car.setHandbrakeAction(handbrakeAction);
            car.setSteerAction(steerAction);
            car.setThrottleAction(throttleAction);
        }

        MovePlayerMessage message = new MovePlayerMessage(player.getPlayerId(), steerAction, handbrakeAction, throttleAction);
        playersIngame.stream()
                .filter(p -> !p.equals(player))
                .map(PlayerWrapper::getConnection)
                .forEach(c -> c.send(message));
    }

    /**
     * Sets the state of the player to ready and sends this to the other
     * players.
     *
     * @param player The given player, not null.
     */
    public void setPlayerReady(PlayerWrapper player) {
        playersReady.add(player);

        if (playersReady.size() >= session.getRoom().getOccupation()) {
            start();

            PlayerChangedGameStatusMessage message = new PlayerChangedGameStatusMessage(PlayerChangedGameStatusMessage.Status.GAME_RUNNING);
            playersIngame.stream()
                    .map(PlayerWrapper::getConnection)
                    .forEach(c -> c.send(message));
        }
    }

    private void notifyGameStatus() {
        GameStatusMessage message = new GameStatusMessage(game.getStatus());
        session.getRoom().getPlayers().stream()
                .map(PlayerWrapper::getConnection)
                .forEach(c -> c.send(message));
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

        GameServerRmiController controller = GameServer.getInstance().getRmiController();
        for (PlayerWrapper p : playersIngame) {
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

        GameServerRmiController controller = GameServer.getInstance().getRmiController();
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

    private void sendStopGameMessage() {
        StopGameMessage message = new StopGameMessage();
        playersIngame.stream()
                .map(PlayerWrapper::getConnection)
                .forEach(c -> c.send(message));
    }

    private void initializeWorldObjects() {
        RoomWrapper room = session.getRoom();
        Map map = game.getMap();

        MapUtilities.addWalls(engine, map);
        CarUtilities.addCars(this, room.getTeamBlue(), room.getTeamRed());

        BallPhysics ball = new BallPhysics(engine, map.getBall());
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

    private void sendBall(PlayerWrapper player) {
        Ball ball = game.getMap().getBall();

        player.getConnection().send(new SpawnBallMessage(ball.getX(), ball.getY(), ball.getDegree(), ball.getBallType()));
    }

    private List<Message> getSynchronisationMessages() {
        List<Message> messages = new ArrayList<>();

        for (PlayerWrapper p : playersIngame) {
            CarPhysics car = engine.getCarFromPlayer(p.unwrap());
            if (car.isResetting()) {
                continue;
            }

            messages.add(new PlayerSyncMessage(p.getPlayerId(), car.getX(), car.getY(), car.getDegree(), car.getLinearVelocityX(), car.getLinearVelocityY(), car.getAngularVelocity()));
        }

        BallPhysics ball = engine.getBall();
        if (!ball.isResetting()) {
            messages.add(new BallSyncMessage(ball.getX(), ball.getY(), ball.getLinearVelocityX(), ball.getLinearVelocityY(), ball.getAngularVelocity()));
        }

        return messages;
    }

    /**
     * Gets the GameStatus of the Game.
     *
     * @return The GameStatus of the Game.
     */
    public GameStatus getGameStatus() {
        return game.getStatus();
    }

    /**
     * Gets the Game Engine.
     *
     * @return GameEngine The game engine.
     */
    public GameEngine getGameEngine() {
        return engine;
    }

    /**
     * Gets the game settings.
     *
     * @return GameSettings The game settings.
     */
    public GameSettings getGameSettings() {
        return game.getGameSettings();
    }

    /**
     * Gets the Map.
     *
     * @return Map The map.
     */
    public Map getMap() {
        return game.getMap();
    }

}
