package hr.nipeta.kqzero.movement.traversal;

import hr.nipeta.kqzero.gameobjects.entities.Direction;
import hr.nipeta.kqzero.movement.Movement;
import lombok.AllArgsConstructor;

import java.util.Random;

/**
 * Turn random after fixed straight line traversal (it is also possible to randomly choose current direction again)
 */
@AllArgsConstructor
public class FixedRandomTraversalStrategy implements TraversalStrategy {

    private static final Direction[] DIRECTIONS = Direction.values();
    private final Random random = new Random();
    private double currentDirectionMaxDistance;

    @Override
    public void apply(Movement movement) {
        if (movement.getCurrentDirectionDistance() > currentDirectionMaxDistance) {
            Direction newDirection = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            movement.changeDirection(newDirection);
        }
    }

}
