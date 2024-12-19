package hr.nipeta.game2d.entities.enemies;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.collision.CollisionTolerance;
import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.world.tiles.Tile;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class BlobLight extends Enemy {

    public BlobLight(GameManager gm, double worldTileX, double worldTileY) {
        super(gm, worldTileX, worldTileY, 2, new CollisionTolerance(0.3d), Tile.water());
    }

    @Override
    public void update(double deltaTimeInSeconds) {

        // Track when was last sprite updated
        spriteCounter.addToLastChangeInSeconds(deltaTimeInSeconds);

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

}
