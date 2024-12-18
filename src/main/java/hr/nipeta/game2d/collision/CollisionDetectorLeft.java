package hr.nipeta.game2d.collision;

import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.world.World;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollisionDetectorLeft extends CollisionDetectorLeftRight {

    @Override
    protected int calculateCurrentTileX(Entity entity) {
        return (int)Math.floor(entity.worldTileX + entity.collisionTolerance.left);
    }

    @Override
    protected int calculateNextTileX(Entity entity, double tileDistanceTraveled) {
        return (int)Math.floor(entity.worldTileX + entity.collisionTolerance.left - tileDistanceTraveled);
    }

    @Override
    protected boolean outOfMapX(World world, int tileX) {
        return tileX < 0;
    }
}
