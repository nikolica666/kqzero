package hr.nipeta.kqzero.collision;

import hr.nipeta.kqzero.gameobjects.entities.Entity;
import hr.nipeta.kqzero.world.World;
import hr.nipeta.kqzero.world.tiles.Tile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CollisionDetector {

    /**
     * <ol>
     *     <li>Find out current and next tile indices along <b>movement</b> axis (LR = X-axis, UD = Y-axis)</li>
     *     <li>If indices are same, we're still on same tile, so there's no collision (return <b>false</b>)</li>
     *     <li>If next tile index along movement axis is out of map that's collision(return <b>true</b>)</li>
     *     <li>Find out indices along <b>collision</b> axis (for LR that's Y-axis, for UD that's X-axis). There
     *     can only be one or two tiles in which entity will hit</li>
     *     <li>Get those tiles (or single tile) from world</li>
     *     <li>Check if entity collides with them</li>
     * </ol>
     *
     */
    public boolean check(World world, Entity entity, double tileDistanceTraveled) {

        // e.g. for moving left on tile (x=2,y=3), this may be 2 (if it's still on same tile), or 1 if on new
        int nextTileIndexAlongMovementAxis = calculateNextTileIndexAlongMovementAxis(entity, tileDistanceTraveled);

        if (indexOutOfMap(world, nextTileIndexAlongMovementAxis)) {
            // outside of map =  collision (e.g. for moving left it means X-coord after travel would be < 0)
            return true;
        }

        // e.g. for moving left  on tile (x=2,y=3), this is 2
        int currentTileIndexAlongMovementAxis = calculateCurrentTileIndexAlongMovementAxis(entity);

        if (nextTileIndexAlongMovementAxis == currentTileIndexAlongMovementAxis) {
            // We'll still be on the same tile, so there's no collision
            return false;
        }

        // e.g. for move left on tile (x=2,y=3), these indices may be y=2 and y=1, or y=2 and y=2, or y=2 and y=3
        TileIndices collisionAxisTileIndices = collisionAxisTileIndices(entity);

        // Mini optimization if there is just 1 tile to check
        if (collisionAxisTileIndices.isSameIndex()) {
            Tile tile = collisionTile(world, collisionAxisTileIndices.index1, nextTileIndexAlongMovementAxis);
            return entity.collidesWith(tile);
        } else {
            Tile tile1 = collisionTile(world, collisionAxisTileIndices.index1, nextTileIndexAlongMovementAxis);
            Tile tile2 = collisionTile(world, collisionAxisTileIndices.index2, nextTileIndexAlongMovementAxis);
            return entity.collidesWith(tile1) || entity.collidesWith(tile2);

        }

    }

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
