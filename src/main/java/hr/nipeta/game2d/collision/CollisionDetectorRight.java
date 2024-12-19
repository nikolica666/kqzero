package hr.nipeta.game2d.collision;

import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.world.World;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollisionDetectorRight extends CollisionDetectorLeftRight {

    @Override
    protected int calculateCurrentTileIndexAlongMovementAxis(Entity entity) {
        return (int)Math.ceil(entity.worldTileX - entity.collisionTolerance.right);
    }

    @Override
    protected int calculateNextTileIndexAlongMovementAxis(Entity entity, double tileDistanceTraveled) {
        return (int)Math.ceil(entity.worldTileX - entity.collisionTolerance.right + tileDistanceTraveled);
    }

    @Override
    protected boolean indexOutOfMap(World world, int nextMovementAxis) {
        return nextMovementAxis > world.COLS_TOTAL - 1;
    }
}
