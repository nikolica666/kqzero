package hr.nipeta.game2d.entities.neutral;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.collision.CollisionTolerance;

import java.util.Collections;
import java.util.Random;

public class Bird extends Neutral {

    public Bird(GameManager gm, double worldTileX, double worldTileY) {
        super(gm, worldTileX, worldTileY, new Random().nextInt(8,12), new CollisionTolerance(.4), Collections.emptySet());
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
                case UP -> this.worldTileY -= tileDistanceTraveled;
                case DOWN -> this.worldTileY += tileDistanceTraveled;
                case LEFT -> this.worldTileX -= tileDistanceTraveled;
                case RIGHT -> this.worldTileX += tileDistanceTraveled;
                default -> throw new UnsupportedOperationException();
            }

            totalTileDistanceTraveled += tileDistanceTraveled;

            sameDirectionTotalTileDistanceTraveled += tileDistanceTraveled;

            if (sameDirectionTotalTileDistanceTraveled > 7) {
                direction = Direction.values()[new Random().nextInt(Direction.values().length)];
                sameDirectionTotalTileDistanceTraveled = 0;
            }

        } else {

            switch (direction) {
                case UP -> this.direction = Direction.RIGHT;
                case DOWN -> this.direction = Direction.LEFT;
                case LEFT -> this.direction = Direction.UP;
                case RIGHT -> this.direction = Direction.DOWN;
                default -> throw new UnsupportedOperationException();
            }

        }

    }

}
