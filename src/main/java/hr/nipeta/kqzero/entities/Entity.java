package hr.nipeta.kqzero.entities;

import hr.nipeta.kqzero.SpriteCounter;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.world.tiles.Tile;

import java.util.Set;

public abstract class Entity {

    protected final GameManager gm;
    public double worldTileX;
    public double worldTileY;
    public final double speedTilesPerSecond;
    public final CollisionTolerance collisionTolerance;
    public Direction direction = Direction.DOWN;
    public double totalTileDistanceTraveled;
    public double sameDirectionTotalTileDistanceTraveled;
    private final Set<Tile> collidesWith;
    public final SpriteCounter spriteCounter;

    protected Entity(GameManager gm, double worldTileX, double worldTileY, double speedTilesPerSecond, CollisionTolerance collisionTolerance, Set<Tile> collidesWith) {
        this.gm = gm;
        this.worldTileX = worldTileX;
        this.worldTileY = worldTileY;
        this.speedTilesPerSecond = speedTilesPerSecond;
        this.collisionTolerance = collisionTolerance;
        this.collidesWith = collidesWith;
        this.spriteCounter = new SpriteCounter(0.16d);
    }

    public abstract void update(double deltaTimeInSeconds);
    public abstract void draw();

    public boolean collidesWith(Tile tile) {
        return collidesWith.contains(tile);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

}
