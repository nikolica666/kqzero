package hr.nipeta.game2d.entities;

import hr.nipeta.game2d.SpriteCounter;
import hr.nipeta.game2d.collision.CollisionTolerance;
import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.world.tiles.Tile;

import java.util.Set;

public abstract class Entity {

    protected GameManager gm;
    public double worldTileX, worldTileY;
    public double speedTilesPerSecond;
    public CollisionTolerance collisionTolerance;
    public Direction direction = Direction.DOWN;
    public boolean moving;
    public double totalTileDistanceTraveled;
    public double sameDirectionTotalTileDistanceTraveled;
    private Set<Tile> collidesWith;
    public SpriteCounter spriteCounter;

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
