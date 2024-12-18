package hr.nipeta.game2d.collision;

import hr.nipeta.game2d.DebugConfig;
import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.world.World;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CollisionDetectorUpDown implements CollisionDetector {

    protected abstract int calculateCurrentTileY(Entity entity);
    protected abstract int calculateNextTileY(Entity entity, double tileDistanceTraveled);
    protected abstract boolean outOfMapY(World world, int tileY);

    @Override
    public boolean check(World world, Entity entity, double tileDistanceTraveled) {

        int currentTileY = calculateCurrentTileY(entity);
        int nextTileY = calculateNextTileY(entity, tileDistanceTraveled);

        // We're still in same tile, so no collision
        if (nextTileY == currentTileY) {
            return false;
        }

        // We're outside of map
        if (outOfMapY(world, nextTileY)) {
            return true;
        }

        // Set tolerances
        double collisionLeftX = entity.worldTileX + entity.collisionTolerance.left;
        // Math.floor will turn him to -1 sometimes and this way is faster anyway
        int collisionTileLeftX = (int)collisionLeftX;

        // Let's do the same for right tolerance
        double collisionRightX = entity.worldTileX + 1 - entity.collisionTolerance.right;
        int collisionTileRightX = (int)collisionRightX;

        if (DebugConfig.logEntityCollision) {
            log.debug("nextTileY={} (entity.worldTileY={}, tileDistanceTraveled={})", nextTileY, entity.worldTileY, tileDistanceTraveled);
            log.debug("collisionLeftX={}, collisionRightX={}",collisionLeftX, collisionRightX);
            log.debug("collisionTileLeftX={}, collisionTileRightX={}",collisionTileLeftX, collisionTileRightX);
        }

        if (collisionTileLeftX == collisionTileRightX) {
            return entity.collidesWith.contains(world.tiles[nextTileY][collisionTileLeftX]);
        } else {
            return entity.collidesWith.contains(world.tiles[nextTileY][collisionTileLeftX]) ||
                    entity.collidesWith.contains(world.tiles[nextTileY][collisionTileRightX]);
        }

    }

}
