package hr.nipeta.kqzero.gameobjects.entities.neutral;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.gameobjects.Direction;
import hr.nipeta.kqzero.movement.Movement;
import hr.nipeta.kqzero.world.tiles.Tile;

import java.util.Random;

public class Fish extends Neutral {

    public Fish(GameManager gameManager, double worldTileX, double worldTileY) {
        super(gameManager, worldTileX, worldTileY, new CollisionTolerance(.1), Tile.nonWater(), Movement.simple(2.5d));
    }

    @Override
    public void update(double deltaTimeInSeconds) {

        // Track when was last sprite updated
        spriteCounter.addToLastChangeInSeconds(deltaTimeInSeconds);

        // Set sprite index if needed, so draw() will switch
        spriteCounter.incrementCounterIfSpriteChangeNeeded();

        double tileDistanceTraveled = movement.getSpeed().getCurrent() * deltaTimeInSeconds;

        boolean hasCollidedWithTile = gm.collisionManager.check(gm.world, this, tileDistanceTraveled);

        if (!hasCollidedWithTile) {

            switch (movement.getDirection()) {
                case UP -> this.worldTileY -= tileDistanceTraveled;
                case DOWN -> this.worldTileY += tileDistanceTraveled;
                case LEFT -> this.worldTileX -= tileDistanceTraveled;
                case RIGHT -> this.worldTileX += tileDistanceTraveled;
                default -> throw new UnsupportedOperationException();
            }

            totalTileDistanceTraveled += tileDistanceTraveled;

            sameDirectionTotalTileDistanceTraveled += tileDistanceTraveled;

            if (sameDirectionTotalTileDistanceTraveled > 1.5) {
                movement.setDirection(Direction.values()[new Random().nextInt(Direction.values().length)]);
                sameDirectionTotalTileDistanceTraveled = 0;
            }

        } else {

            switch (movement.getDirection()) {
                case UP -> movement.setDirection(Direction.RIGHT);
                case DOWN -> movement.setDirection(Direction.LEFT);
                case LEFT -> movement.setDirection(Direction.UP);
                case RIGHT -> movement.setDirection(Direction.DOWN);
                default -> throw new UnsupportedOperationException();
            }

        }

    }

    @Override
    public String getName() {
        return "Fish";
    }
}
