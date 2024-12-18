package hr.nipeta.game2d.entities.enemies;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.SpriteManager;
import hr.nipeta.game2d.collision.CollisionTolerance;
import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.world.tiles.Tile;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class BlobLight extends Enemy {

    public BlobLight(GameManager gm, double worldTileX, double worldTileY) {
        super(gm, worldTileX, worldTileY, 2, new CollisionTolerance(0.4d), Tile.water());
    }

    @Override
    public void update(double deltaTimeInSeconds) {

        // Track when was last sprite updated
        spriteCounter.lastChangeInSeconds += deltaTimeInSeconds;

        // Set sprite index if needed, so draw() will switch
        spriteCounter.incrementCounterIfSpriteChangeNeeded();

        double tileDistanceTraveled = this.speedTilesPerSecond * deltaTimeInSeconds;

        boolean hasCollided = gm.collisionManager.check(gm.world, this, tileDistanceTraveled);

        if (!hasCollided) {

            switch (direction) {
                case Entity.Direction.UP -> this.worldTileY -= tileDistanceTraveled;
                case Entity.Direction.DOWN -> this.worldTileY += tileDistanceTraveled;
                case Entity.Direction.LEFT -> this.worldTileX -= tileDistanceTraveled;
                case Entity.Direction.RIGHT -> this.worldTileX += tileDistanceTraveled;
                default -> throw new UnsupportedOperationException();
            }

            totalTileDistanceTraveled += tileDistanceTraveled;

            sameDirectionTotalTileDistanceTraveled += tileDistanceTraveled;

            if (sameDirectionTotalTileDistanceTraveled > 3) {
                direction = Entity.Direction.values()[new Random().nextInt(Entity.Direction.values().length)];
                sameDirectionTotalTileDistanceTraveled = 0;
            }

        } else {

            switch (direction) {
                case Entity.Direction.UP -> this.direction = Entity.Direction.RIGHT;
                case Entity.Direction.DOWN -> this.direction = Entity.Direction.LEFT;
                case Entity.Direction.LEFT -> this.direction = Entity.Direction.UP;
                case Entity.Direction.RIGHT -> this.direction = Entity.Direction.DOWN;
                default -> throw new UnsupportedOperationException();
            }

        }

    }

    @Override
    public void draw() {

        double relativeToPlayerX = gm.player.worldTileX - this.worldTileX;
        double relativeToPlayerY = gm.player.worldTileY - this.worldTileY;

        if (Math.abs(relativeToPlayerX) > (double) gm.world.COLS_TOTAL / 2) {
            return;
        }

        if (Math.abs(relativeToPlayerY) > (double) gm.world.ROWS_TOTAL / 2) {
            return;
        }

        // TODO possible optimization? We have to call spriteManager only when sprite will change, not every time...
        SpriteManager.SpriteSheetResult spriteSheetInfo = gm.spriteManager.calculateEnemySpriteSheet(SpriteManager.Enemies.BLOB_LIGHT, this.direction, this.spriteCounter.counter);

        gm.gc.drawImage(
                spriteSheetInfo.getSpriteSheet(),
                spriteSheetInfo.getSource().getMinX(),
                spriteSheetInfo.getSource().getMinY(),
                spriteSheetInfo.getSource().getWidth(),
                spriteSheetInfo.getSource().getHeight(),
                gm.CENTRAL_TILE_TOP_LEFT_X - relativeToPlayerX * gm.TILE_SIZE,
                gm.CENTRAL_TILE_TOP_LEFT_Y - relativeToPlayerY * gm.TILE_SIZE,
                gm.TILE_SIZE,
                gm.TILE_SIZE);


    }
}
