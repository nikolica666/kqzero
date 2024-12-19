package hr.nipeta.kqzero.collision;

import hr.nipeta.kqzero.gameobjects.entities.Entity;
import hr.nipeta.kqzero.world.World;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollisionDetectorDown extends CollisionDetectorUpDown {

    @Override
    protected int calculateCurrentTileIndexAlongMovementAxis(Entity entity) {
        return (int)Math.ceil(entity.worldTileY - entity.collisionTolerance.bot);
    }

    @Override
    protected int calculateNextTileIndexAlongMovementAxis(Entity entity, double tileDistanceTraveled) {
        return (int)Math.ceil(entity.worldTileY - entity.collisionTolerance.bot + tileDistanceTraveled);
    }

    @Override
    protected boolean indexOutOfMap(World world, int nextMovementAxis) {
        return nextMovementAxis > world.ROWS_TOTAL - 1;
    }
}
