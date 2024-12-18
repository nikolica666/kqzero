package hr.nipeta.game2d.collision;

import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.world.World;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollisionDetectorDown extends CollisionDetectorUpDown {

    @Override
    protected int calculateCurrentTileY(Entity entity) {
        return (int)Math.ceil(entity.worldTileY - entity.collisionTolerance.bot);
    }

    @Override
    protected int calculateNextTileY(Entity entity, double tileDistanceTraveled) {
        return (int)Math.ceil(entity.worldTileY - entity.collisionTolerance.bot + tileDistanceTraveled);
    }

    @Override
    protected boolean outOfMapY(World world, int tileY) {
        return tileY > world.ROWS_TOTAL - 1;
    }

}
