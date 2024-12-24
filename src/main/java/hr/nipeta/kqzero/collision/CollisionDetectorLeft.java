package hr.nipeta.kqzero.collision;

import hr.nipeta.kqzero.gameobjects.entities.Entity;
import hr.nipeta.kqzero.world.World;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollisionDetectorLeft extends CollisionDetectorLeftRight {

    @Override
    protected int calculateCurrentTileIndexAlongMovementAxis(Entity entity) {
        return (int)Math.floor(entity.tile.x + entity.collisionTolerance.left);
    }

    @Override
    protected int calculateNextTileIndexAlongMovementAxis(Entity entity, double tileDistanceTraveled) {
        return (int)Math.floor(entity.tile.x + entity.collisionTolerance.left - tileDistanceTraveled);
    }

    @Override
    protected boolean indexOutOfMap(World world, int nextMovementAxis) {
        return nextMovementAxis < 0;
    }
}
