package nl.soccar.gameserver.util;

import javafx.scene.shape.Rectangle;
import nl.soccar.gameserver.model.session.GameWrapper;
import nl.soccar.library.*;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.physics.GameEngine;
import nl.soccar.physics.models.CarPhysics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utilities for the Car.
 *
 * @author PTS34A
 */
public final class CarUtilities {

    private CarUtilities() {
    }

    /**
     * Adds the Cars to the Game.
     *
     * @param game The given game, not null.
     * @param blue The given team blue, not null.
     * @param red The given team red, not null.
     */
    public static void addCars(GameWrapper game, Team blue, Team red) {
        addCars(game, blue);
        addCars(game, red);
    }

    private static void addCars(GameWrapper game, Team team) {
        List<Player> players = new ArrayList<>(team.getPlayers());
        Collections.shuffle(players);

        int teamSize = players.size();
        for (int i = 0; i < teamSize; i++) {
            Player player = players.get(i);
            addCar(game, player, team, teamSize, i);
        }
    }

    private static void addCar(GameWrapper game, Player player, Team team, int teamSize, int number) {
        GameEngine engine = game.getGameEngine();
        Map map = game.getMap();

        Rectangle size = map.getSize();
        float width = (float) size.getWidth();
        float height = (float) size.getHeight();

        Ball ball = map.getBall();
        float ballX = ball.getX();
        float ballY = ball.getY();

        TeamColour colour = team.getTeamColour();

        float x = colour == TeamColour.BLUE ? 30.0F : width - 30.0F;
        float y = (height / (teamSize + 1)) * (number + 1);
        float degree = getAngle(x, y, ballX, ballY) - 90;

        Car car = new Car(x, y, degree, player.getCarType(), player);
        map.addCar(car);

        CarPhysics physics = new CarPhysics(engine, car);
        engine.addCar(player, physics);
    }

    private static float getAngle(float sourceX, float sourceY, float targetX, float targetY) {
        float angle = (float) Math.toDegrees(Math.atan2(targetY - sourceY, targetX - sourceX));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

}
