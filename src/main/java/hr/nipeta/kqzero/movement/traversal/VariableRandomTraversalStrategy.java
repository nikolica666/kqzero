package hr.nipeta.kqzero.movement.traversal;

import hr.nipeta.kqzero.movement.Movement;

import java.util.Random;

import static hr.nipeta.kqzero.gameobjects.entities.Direction.random;

/**
 * Turn random after variable straight line traversal (it is also possible to randomly choose current direction again)
 * <p>After turning next straight line distance is chosen randomly</p>
 */
public class VariableRandomTraversalStrategy implements TraversalStrategy {

    private final Random random = new Random();

    // We use these 2 to randomly set next max allowed straight line traversal (we do this when direction is changed)
    private final double minRandomMaxDistance;
    private final double maxRandomMaxDistance;

    private double currentMaxDistance;

    /**
     * @param minRandomMaxDistance min distance to travel before (random) turn
     * @param maxRandomMaxDistance max distance to travel before (random) turn
     * @see VariableRandomTraversalStrategy
     */
    public VariableRandomTraversalStrategy(double minRandomMaxDistance, double maxRandomMaxDistance) {
        this.minRandomMaxDistance = minRandomMaxDistance;
        this.maxRandomMaxDistance = maxRandomMaxDistance;
        this.currentMaxDistance = random.nextDouble(minRandomMaxDistance, maxRandomMaxDistance);
    }

    @Override
    public void apply(Movement movement) {

        if (movement.getCurrentDirectionDistance() > currentMaxDistance) {
            movement.changeDirection(random());

            // Reset distance after turning (that's what we mean by 'variable traversal')
            currentMaxDistance = random.nextDouble(minRandomMaxDistance, maxRandomMaxDistance);

        }

    }

}
