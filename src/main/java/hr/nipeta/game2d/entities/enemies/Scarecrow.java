package hr.nipeta.game2d.entities.enemies;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.collision.CollisionTolerance;
import hr.nipeta.game2d.world.tiles.Tile;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class Scarecrow extends Enemy {

    private double sameDirectionTotalTileDistanceTraveled;

    public Scarecrow(GameManager gm, double worldTileX, double worldTileY) {
        super(gm, worldTileX, worldTileY, 0.5d, new CollisionTolerance(0.3d), Tile.waterOrSolid());
    }

    @Override
    public void update(double deltaTimeInSeconds) {

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

            if (sameDirectionTotalTileDistanceTraveled > 5) {
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
