package hr.nipeta.game2d.collision;

import hr.nipeta.game2d.DebugConfig;
import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.world.World;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CollisionDetectorLeftRight implements CollisionDetector {

    protected abstract int calculateCurrentTileX(Entity entity);
    protected abstract int calculateNextTileX(Entity entity, double tileDistanceTraveled);
    protected abstract boolean outOfMapX(World world, int tileX);

    @Override
    public boolean check(World world, Entity entity, double tileDistanceTraveled) {

        int currentTileX = calculateCurrentTileX(entity);
        int nextTileX = calculateNextTileX(entity, tileDistanceTraveled);

        // We're still in same tile, so no collision
        if (nextTileX == currentTileX) {
            return false;
        }

        // We're outside of map
        if (outOfMapX(world, nextTileX)) {
            return true;
        }

        // Set tolerances
        // By 'Bot' we mean lowest row number, i.e. row visually ABOVE or EVEN
        double collisionBotY = entity.worldTileY + entity.collisionTolerance.top;
        // Math.floor will turn him to -1 sometimes and this way is faster anyway
        int collisionTileBotY = (int)collisionBotY;

        // Let's do the same for row number + 1, i.e. row visually EVEN or BELOW
        double collisionTopY = entity.worldTileY + 1 - entity.collisionTolerance.bot;
        int collisionTileTopY = (int)collisionTopY;

        if (DebugConfig.logEntityCollision) {
            log.debug("nextTileX={} (entityWorldX={}, entityWorldY={},tileDistance={})", nextTileX, entity.worldTileX,entity.worldTileY, tileDistanceTraveled);
            log.debug("collisionBotY={}, collisionTopY={}",collisionBotY, collisionTopY);
            log.debug("collisionTileBotY={}, collisionTileTopY={}",collisionTileBotY, collisionTileTopY);
        }

        if (collisionTileBotY == collisionTileTopY) {
            return entity.collidesWith.contains(world.tiles[collisionTileBotY][nextTileX]);
        } else {
            return entity.collidesWith.contains(world.tiles[collisionTileBotY][nextTileX]) ||
                    entity.collidesWith.contains(world.tiles[collisionTileTopY][nextTileX]);
        }

    }

}
