package hr.nipeta.kqzero.movement.collision;

import hr.nipeta.kqzero.movement.Movement;

public interface CollisionStrategy {
    void apply(Movement movement);
}
