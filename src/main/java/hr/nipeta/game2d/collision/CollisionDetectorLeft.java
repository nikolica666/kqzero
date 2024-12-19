package hr.nipeta.game2d.collision;

import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.world.World;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollisionDetectorLeft extends CollisionDetectorLeftRight {

    @Override
    protected int calculateCurrentTileIndexAlongMovementAxis(Entity entity) {
        return (int)Math.floor(entity.worldTileX + entity.collisionTolerance.left);
    }

    @Override
    protected int calculateNextTileIndexAlongMovementAxis(Entity entity, double tileDistanceTraveled) {
        return (int)Math.floor(entity.worldTileX + entity.collisionTolerance.left - tileDistanceTraveled);
    }

    @Override
    protected boolean indexOutOfMap(World world, int nextMovementAxis) {
        return nextMovementAxis < 0;
    }
}
