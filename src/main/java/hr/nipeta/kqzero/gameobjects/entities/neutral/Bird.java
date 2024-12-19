package hr.nipeta.kqzero.gameobjects.entities.neutral;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.gameobjects.entities.Direction;
import hr.nipeta.kqzero.movement.Movement;

import java.util.Collections;
import java.util.Random;

public class Bird extends Neutral {

    public Bird(GameManager gm, double worldTileX, double worldTileY) {
        super(gm, worldTileX, worldTileY, new CollisionTolerance(.4), Collections.emptySet(), Movement.simple(new Random().nextInt(3,8)));
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

            if (sameDirectionTotalTileDistanceTraveled > 7) {
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
        return "Bird";
    }
}
