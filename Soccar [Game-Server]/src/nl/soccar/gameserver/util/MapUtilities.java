package nl.soccar.gameserver.util;

import javafx.scene.shape.Rectangle;
import nl.soccar.library.Map;
import nl.soccar.library.Obstacle;
import nl.soccar.library.enumeration.ObstacleType;
import nl.soccar.physics.GameEngine;
import nl.soccar.physics.models.ObstaclePhysics;
import org.jbox2d.dynamics.World;

/**
 * Utilites for the Map.
 *
 * @author PTS34A
 */
public final class MapUtilities {

    private static final float WALL_WIDTH = 5;

    private MapUtilities() {
    }

    /**
     * Adds wall to the Map.
     *
     * @param engine The given game engine, not null.
     * @param map The given map, not null.
     */
    public static void addWalls(GameEngine engine, Map map) {
        Rectangle size = map.getSize();
        float mapWidth = (float) size.getWidth();
        float mapHeight = (float) size.getHeight();

        addWestWalls(engine, map, mapHeight);
        addEastWalls(engine, map, mapWidth, mapHeight);
        addNorthAndSouthWalls(engine, mapWidth, mapHeight);
    }

    /**
     * Method that adds the obstacle drawables to the map that represent the
     * west wall.
     *
     * @param mapHeight The height of the map.
     */
    private static void addWestWalls(GameEngine engine, Map map, float mapHeight) {
        World world = engine.getWorld();

        Rectangle leftGoal = map.getGoalBlue();
        float leftGoalY = (float) leftGoal.getY();

        ObstaclePhysics westWallUpper = new ObstacleBuilder(world)
                .x(WALL_WIDTH / 2).y((mapHeight + leftGoalY) / 2).degree(0)
                .width(WALL_WIDTH).height(mapHeight - leftGoalY)
                .type(ObstacleType.WALL).build();

        ObstaclePhysics westWallLower = new ObstacleBuilder(world)
                .x(WALL_WIDTH / 2).y((mapHeight - leftGoalY) / 2).degree(0)
                .width(WALL_WIDTH).height(mapHeight - leftGoalY)
                .type(ObstacleType.WALL).build();

        ObstaclePhysics westWallMiddle = new ObstacleBuilder(world)
                .x(-WALL_WIDTH).y(mapHeight / 2).degree(0)
                .width(WALL_WIDTH).height(mapHeight)
                .type(ObstacleType.WALL).build();

        engine.addWorldObject(westWallUpper);
        engine.addWorldObject(westWallLower);
        engine.addWorldObject(westWallMiddle);
    }

    /**
     * Method that adds the obstacle drawables to the map that represent the
     * easts walls.
     *
     * @param mapWidth The width of the map.
     * @param mapHeight The height of the map.
     */
    private static void addEastWalls(GameEngine engine, Map map, float mapWidth, float mapHeight) {
        World world = engine.getWorld();

        Rectangle rightGoal = map.getGoalRed();
        float rightGoalY = (float) rightGoal.getY();

        ObstaclePhysics eastWallUpper = new ObstacleBuilder(world)
                .x(mapWidth - (WALL_WIDTH / 2)).y((mapHeight + rightGoalY) / 2).degree(0)
                .width(WALL_WIDTH).height(mapHeight - rightGoalY)
                .type(ObstacleType.WALL).build();

        ObstaclePhysics eastWallLower = new ObstacleBuilder(world)
                .x(mapWidth - (WALL_WIDTH / 2)).y((mapHeight - rightGoalY) / 2).degree(0)
                .width(WALL_WIDTH).height(mapHeight - rightGoalY)
                .type(ObstacleType.WALL).build();

        ObstaclePhysics eastWallMiddle = new ObstacleBuilder(world)
                .x(mapWidth + WALL_WIDTH).y(mapHeight / 2).degree(0)
                .width(WALL_WIDTH).height(mapHeight)
                .type(ObstacleType.WALL).build();

        engine.addWorldObject(eastWallUpper);
        engine.addWorldObject(eastWallLower);
        engine.addWorldObject(eastWallMiddle);
    }

    /**
     * Method that adds the obstacle drawables to the map that represent the
     * north and south walls.
     *
     * @param mapWidth The width of the map.
     * @param mapHeight The height of the map.
     */
    private static void addNorthAndSouthWalls(GameEngine engine, float mapWidth, float mapHeight) {
        World world = engine.getWorld();

        ObstaclePhysics northWall = new ObstacleBuilder(world)
                .x(mapWidth / 2).y(mapHeight - (WALL_WIDTH / 2)).degree(0)
                .width(mapWidth).height(WALL_WIDTH)
                .type(ObstacleType.WALL).build();

        ObstaclePhysics southWall = new ObstacleBuilder(world)
                .x(mapWidth / 2).y(WALL_WIDTH / 2).degree(0)
                .width(mapWidth).height(WALL_WIDTH)
                .type(ObstacleType.WALL).build();

        engine.addWorldObject(northWall);
        engine.addWorldObject(southWall);
    }

    /**
     * An ObstacleBuilder builds an obstacle for usage with the UI. It combines
     * a model, physics-model and ui object and builds them implicitely.
     */
    public static class ObstacleBuilder {

        private final World world;

        private float x;
        private float y;
        private float degree;
        private float width;
        private float height;
        private ObstacleType type;

        /**
         * Initiates a new ObstacleBuilder with the given parameters.
         *
         * @param world The world that will be used to create the physics-model.
         */
        public ObstacleBuilder(World world) {
            this.world = world;
        }

        /**
         * Sets the x-position of this Obstacle.
         *
         * @param x The new x-position that will be used when building this
         * obstacle.
         * @return This ObstacleBuilder, for method chaining.
         */
        public ObstacleBuilder x(float x) {
            this.x = x;
            return this;
        }

        /**
         * Sets the y-position of this Obstacle.
         *
         * @param y The new y-position that will be used when building this
         * obstacle.
         * @return This ObstacleBuilder, for method chaining.
         */
        public ObstacleBuilder y(float y) {
            this.y = y;
            return this;
        }

        /**
         * Sets the angle of this Obstacle.
         *
         * @param degree The new angle that will be used when building this
         * obstacle.
         * @return This ObstacleBuilder, for method chaining.
         */
        public ObstacleBuilder degree(float degree) {
            this.degree = degree;
            return this;
        }

        /**
         * Sets the width of this Obstacle.
         *
         * @param width The new width that will be used when building this
         * obstacle.
         * @return This ObstacleBuilder, for method chaining.
         */
        public ObstacleBuilder width(float width) {
            this.width = width;
            return this;
        }

        /**
         * Sets the height of this Obstacle.
         *
         * @param height The new height that will be used when building this
         * obstacle.
         * @return This ObstacleBuilder, for method chaining.
         */
        public ObstacleBuilder height(float height) {
            this.height = height;
            return this;
        }

        /**
         * Sets the type of this Obstacle.
         *
         * @param type The new type that will be used when building this
         * obstacle.
         * @return This ObstacleBuilder, for method chaining.
         */
        public ObstacleBuilder type(ObstacleType type) {
            this.type = type;
            return this;
        }

        /**
         * Builds an Obstacle-UI object, it combines a model and a physics-model
         * to do so.
         *
         * @return The created ObstacleUiFx object.
         */
        public ObstaclePhysics build() {
            Obstacle obstacle = new Obstacle(x, y, degree, width, height, type);
            return new ObstaclePhysics(obstacle, world);
        }

    }

}
