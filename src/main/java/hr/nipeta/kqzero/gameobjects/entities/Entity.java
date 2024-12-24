package hr.nipeta.kqzero.gameobjects.entities;

import hr.nipeta.kqzero.SpriteCounter;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.gameobjects.GameObject;
import hr.nipeta.kqzero.movement.Movement;
import hr.nipeta.kqzero.world.WorldTile;
import hr.nipeta.kqzero.world.tiles.Tile;
import lombok.Getter;

import java.util.Set;

public abstract class Entity extends GameObject {

    @Getter
    protected final Movement movement;
    private final Set<Tile> collidesWith;
    @Getter
    protected final SpriteCounter spriteCounter;

    protected Entity(GameManager gm, WorldTile worldTile, CollisionTolerance collisionTolerance, Set<Tile> collidesWith, Movement movement) {
        super(gm, worldTile, collisionTolerance);
        this.movement = movement;
        this.collidesWith = collidesWith;
        this.spriteCounter = new SpriteCounter(0.16d);
    }

    public abstract void update(double deltaTimeInSeconds);

    public boolean collidesWith(Tile tile) {
        return collidesWith.contains(tile);
    }

    public enum Action {
        MOVE_OVER_ITEM
    }

}

