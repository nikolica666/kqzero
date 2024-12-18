package hr.nipeta.game2d.collision;

import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.world.World;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollisionDetectorUp extends CollisionDetectorUpDown {

    @Override
    protected int calculateCurrentTileY(Entity entity) {
        return (int)Math.floor(entity.worldTileY + entity.collisionTolerance.top);
    }

    @Override
    protected int calculateNextTileY(Entity entity, double tileDistanceTraveled) {
        return (int)Math.floor(entity.worldTileY + entity.collisionTolerance.top - tileDistanceTraveled);
    }

    @Override
    protected boolean outOfMapY(World world, int tileY) {
        return tileY < 0;
    }

}
