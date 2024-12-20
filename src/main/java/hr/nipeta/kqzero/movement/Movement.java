package hr.nipeta.kqzero.movement;

import hr.nipeta.kqzero.gameobjects.entities.Direction;
import hr.nipeta.kqzero.movement.collision.ClockwiseCollisionStrategy;
import hr.nipeta.kqzero.movement.collision.CollisionStrategy;
import hr.nipeta.kqzero.movement.traversal.FixedRandomTraversalStrategy;
import hr.nipeta.kqzero.movement.traversal.TraversalStrategy;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public final class Movement {

    private Direction direction;
    private Speed speed;
    private CollisionStrategy collisionStrategy;
    private TraversalStrategy traversalStrategy;

    private double totalDistance; // TODO we should increment this after changing world X,Y
    private double currentDirectionDistance; // TODO we should reset this on setDirection

    /**
     * Constant speed, default direction {@link Direction#DOWN down},
     * on collision with tile {@link hr.nipeta.kqzero.movement.collision.ClockwiseCollisionStrategy turn clockwise}
     *
     * @param constantSpeed constant speed (in tiles per second)
     * @return new Movement object
     */
    public static Movement simple(double constantSpeed, double maxTilesInStraightLine) {
        return Movement.builder()
                .direction(Direction.DOWN)
                .speed(Speed.constant(constantSpeed))
                .collisionStrategy(new ClockwiseCollisionStrategy()) // TODO singleton factory or something?
                .traversalStrategy(new FixedRandomTraversalStrategy(maxTilesInStraightLine))
                .build();
    }

    /**
     * 1) Increment {@link #totalDistance}
     * 2) Increment {@link #currentDirectionDistance}
     *
     * @param distanceTraveled distance traveled this frame (unit is 'tiles per frame')
     */
    public void incrementTotalAndCurrentDirectionDistance(double distanceTraveled) {
        totalDistance += distanceTraveled;
        currentDirectionDistance += distanceTraveled;
    }

    /**
     * 1) Change {@link #direction}
     * 2) Set {@link #currentDirectionDistance} to 0
     *
     * @param newDirection new {@link Direction}
     */
    public void changeDirection(Direction newDirection) {
        this.direction = newDirection;
        this.currentDirectionDistance = 0;
    }

    @Getter
    @Setter
    public static class Speed {
        private double initial;
        private double current;
        private double min;
        private double max;

        public static Speed constant(double speedInTilesPerSecond) {
            Speed speed = new Speed();
            speed.initial = speedInTilesPerSecond;
            speed.current = speedInTilesPerSecond;
            speed.min = speedInTilesPerSecond;
            speed.max = speedInTilesPerSecond;
            return speed;
        }

    }

}

