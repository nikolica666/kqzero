package hr.nipeta.game2d.collision;

import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.world.World;
import hr.nipeta.game2d.world.tiles.Tile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CollisionDetector {

    /**
     * We need to know on which tile index entity currently is (along the movement axis)
     */
    protected abstract int calculateCurrentTileIndexAlongMovementAxis(Entity entity);

    /**
     * We need to know on which tile index entity will be after potential traveling (along the movement axis)
     */
    protected abstract int calculateNextTileIndexAlongMovementAxis(Entity entity, double tileDistanceTraveled);

    /**
     * We need to check if next tile will be < 0 or > max rows/cols
     * <p>For top and left we have to check if new index is less then 0</p>
     * <p>For bot we have to check if new index is more then MAX_ROWS - 1</p>
     * <p>For right we have to check if new index is more then MAX_COLS - 1</p>
     */
    protected abstract boolean indexOutOfMap(World world, int index);

    /**
     * <p>When entity is moving along axis, it will hit one or two tiles that are placed perpendicular to its movement.
     * We just need to calculate their 'collision' axis indices, and respect entity's {@link CollisionTolerance}</p>
     * <br/>
     * For left-right movement,
     * <ul>
     *     <li>'collision' axis is Y</li>
     *     <li>first index is {@code playerCurrentIndexY + toleranceTop}</li>
     *     <li>other index is {@code playerCurrentIndexY + 1 - toleranceBot}</li>
     * </ul>
     *
     * For up-down movement,
     * <ul>
     *     <li>'collision' axis is X</li>
     *     <li>first index is {@code playerCurrentIndexX + toleranceLeft}</li>
     *     <li>other index is {@code playerCurrentIndexX + 1 - toleranceRight}</li>
     * </ul>
     */
    protected abstract TileIndices collisionAxisTileIndices(Entity entity);

    /**
     * We have coordinates, but don't know which one is X and which one is Y, it depends on the direction
     * <p>For U-D we are 'hitting' X axis so collision axis is X => {@code world.tiles[movementAxisNextTileIndex][collisionAxisTileIndex]}</p>
     * <p>For L-R we are 'hitting' Y axis so collision axis is Y => {@code world.tiles[collisionAxisTileIndex][movementAxisNextTileIndex]}</p>
     */
    protected abstract Tile collisionTile(World world, int collisionAxisTileIndex, int movementAxisNextTileIndex);

    /**
     * <ol>
     *     <li>Find out current and next tile indices along <b>movement</b> axis (LR = X-axis, UD = Y-axis)</li>
     *     <li>If indices are same, we're still on same tile, so <b>return false</b></li>
     *     <li>If next tile index along movement axis is out of map <b>return true</b></li>
     *     <li>Find out indices along <b>collision</b> axis (for LR that's Y-axis, for UD that's X-axis). There
     *     can only be one or two tiles in which entity will hit</li>
     *     <li>Get those tiles (or single tile) from world</li>
     *     <li>Check if entity collides with them</li>
     * </ol>
     *
     */
    public boolean check(World world, Entity entity, double tileDistanceTraveled) {

        int currentTileIndexAlongMovementAxis = calculateCurrentTileIndexAlongMovementAxis(entity);
        int nextTileIndexAlongMovementAxis = calculateNextTileIndexAlongMovementAxis(entity, tileDistanceTraveled);

        if (nextTileIndexAlongMovementAxis == currentTileIndexAlongMovementAxis) {
            // We'll still be on the same tile, so there's no collision
            return false;
        }

        if (indexOutOfMap(world, nextTileIndexAlongMovementAxis)) {
            // We'll be outside of map, so there is collision
            return true;
        }

        TileIndices collisionAxisTileIndices = collisionAxisTileIndices(entity);

        if (collisionAxisTileIndices.isSameIndex()) {
            Tile tile = collisionTile(world, collisionAxisTileIndices.index1, nextTileIndexAlongMovementAxis);
            return entity.collidesWith(tile);
        } else {
            Tile tile1 = collisionTile(world, collisionAxisTileIndices.index1, nextTileIndexAlongMovementAxis);
            Tile tile2 = collisionTile(world, collisionAxisTileIndices.index2, nextTileIndexAlongMovementAxis);
            return entity.collidesWith(tile1) || entity.collidesWith(tile2);

        }

    }

    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    protected static class TileIndices {
        private int index1;
        private int index2;
        private boolean isSameIndex() {
            return index1 == index2;
        }
    }

}

abstract class CollisionDetectorUpDown extends CollisionDetector {

    @Override
    final protected TileIndices collisionAxisTileIndices(Entity entity) {
        return new TileIndices(
                (int) (entity.worldTileX + entity.collisionTolerance.left),
                (int) (entity.worldTileX + 1 - entity.collisionTolerance.right)
        );
    }

    @Override
    final protected Tile collisionTile(World world, int collisionAxisTileIndex, int movementAxisNextTileIndex) {
        return world.tiles[movementAxisNextTileIndex][collisionAxisTileIndex];
    }
}

abstract class CollisionDetectorLeftRight extends CollisionDetector {

    @Override
    final protected TileIndices collisionAxisTileIndices(Entity entity) {
        return new TileIndices(
                (int) (entity.worldTileY + entity.collisionTolerance.top),
                (int) (entity.worldTileY + 1 - entity.collisionTolerance.bot)
        );
    }

    @Override
    final protected Tile collisionTile(World world, int collisionAxisTileIndex, int movementAxisNextTileIndex) {
        return world.tiles[collisionAxisTileIndex][movementAxisNextTileIndex];
    }
}
