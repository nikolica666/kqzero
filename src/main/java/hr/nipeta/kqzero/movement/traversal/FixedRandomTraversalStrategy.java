package hr.nipeta.kqzero.movement.traversal;

import hr.nipeta.kqzero.gameobjects.entities.Direction;
import hr.nipeta.kqzero.movement.Movement;

import java.util.Random;

/**
 * Turn random after fixed straight line traversal (it is also possible to randomly choose current direction again)
 */
public class FixedRandomTraversalStrategy implements TraversalStrategy {

    private static final Direction[] DIRECTIONS = Direction.values();
    private final Random random = new Random();
    private final double maxDistance;

    /**
     * @param maxDistance distance to travel before (random) turn
     * @see FixedRandomTraversalStrategy
     */
    public FixedRandomTraversalStrategy(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public void apply(Movement movement) {
        if (movement.getCurrentDirectionDistance() > maxDistance) {
            Direction newDirection = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            movement.changeDirection(newDirection);
        }
    }

}
