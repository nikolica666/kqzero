package hr.nipeta.kqzero.gameobjects.entities.enemies;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.gameobjects.entities.Direction;
import hr.nipeta.kqzero.movement.Movement;
import hr.nipeta.kqzero.world.tiles.Tile;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class Scarecrow extends Enemy {

    private double sameDirectionTotalTileDistanceTraveled;

    public Scarecrow(GameManager gm, double worldTileX, double worldTileY) {
        super(gm, worldTileX, worldTileY, new CollisionTolerance(0.3d), Tile.waterOrSolid(), Movement.simple(0.5d));
    }

    @Override
    public void update(double deltaTimeInSeconds) {

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

            if (sameDirectionTotalTileDistanceTraveled > 5) {
                movement.setDirection(Direction.values()[new Random().nextInt(Direction.values().length)]);
                sameDirectionTotalTileDistanceTraveled = 0;
            }

        } else {

            switch (movement.getDirection()) {
                case Direction.UP -> movement.setDirection(Direction.RIGHT);
                case Direction.DOWN -> movement.setDirection(Direction.LEFT);
                case Direction.LEFT -> movement.setDirection(Direction.UP);
                case Direction.RIGHT -> movement.setDirection(Direction.DOWN);
                default -> throw new UnsupportedOperationException();
            }

        }

    }

    @Override
    public String getName() {
        return "Scarecrow";
    }
}
