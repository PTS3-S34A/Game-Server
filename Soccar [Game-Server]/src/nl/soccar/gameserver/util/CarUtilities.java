package nl.soccar.gameserver.util;

import javafx.scene.shape.Rectangle;
import nl.soccar.library.*;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.physics.GameEngine;
import nl.soccar.physics.models.CarPhysics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author PTS34A
 */
public final class CarUtilities {

    private CarUtilities() {
    }

    public static void addCars(GameEngine engine, Map map, Team blue, Team red) {
        addCars(engine, map, blue);
        addCars(engine, map, red);
    }

    private static void addCars(GameEngine engine, Map map, Team team) {
        List<Player> players = new ArrayList<>(team.getPlayers());
        Collections.shuffle(players);

        int teamSize = players.size();
        for (int i = 0; i < teamSize; i++) {
            Player player = players.get(i);
            addCar(engine, map, player, team, teamSize, i);
        }
    }

    private static void addCar(GameEngine engine, Map map, Player player, Team team, int teamSize, int number) {
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

        CarPhysics physics = new CarPhysics(car, engine.getWorld());
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
