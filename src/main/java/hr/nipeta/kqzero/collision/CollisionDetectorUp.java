package hr.nipeta.kqzero.collision;

import hr.nipeta.kqzero.gameobjects.entities.Entity;
import hr.nipeta.kqzero.world.World;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollisionDetectorUp extends CollisionDetectorUpDown {

    @Override
    protected int calculateCurrentTileIndexAlongMovementAxis(Entity entity) {
        return (int)Math.floor(entity.tile.y + entity.collisionTolerance.top);
    }

    @Override
    protected int calculateNextTileIndexAlongMovementAxis(Entity entity, double tileDistanceTraveled) {
        return (int)Math.floor(entity.tile.y + entity.collisionTolerance.top - tileDistanceTraveled);
    }

    @Override
    protected boolean indexOutOfMap(World world, int nextMovementAxis) {
        return nextMovementAxis < 0;
    }
}
