package hr.nipeta.kqzero.movement.collision;

import hr.nipeta.kqzero.movement.Movement;

import static hr.nipeta.kqzero.gameobjects.entities.Direction.*;

public class ClockwiseCollisionStrategy implements CollisionStrategy {

    @Override
    public void apply(Movement movement) {
        switch (movement.getDirection()) {
            case UP -> movement.changeDirection(RIGHT);
            case DOWN -> movement.changeDirection(LEFT);
            case LEFT -> movement.changeDirection(UP);
            case RIGHT -> movement.changeDirection(DOWN);
            default -> throw new UnsupportedOperationException();
        }
    }
}
