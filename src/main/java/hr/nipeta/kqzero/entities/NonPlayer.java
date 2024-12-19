package hr.nipeta.kqzero.entities;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.world.tiles.Tile;

import java.util.Set;

public abstract class NonPlayer extends Entity {

    protected NonPlayer(GameManager gm, double worldTileX, double worldTileY, double speedTilesPerSecond, CollisionTolerance collisionTolerance, Set<Tile> collidesWith) {
        super(gm, worldTileX, worldTileY, speedTilesPerSecond, collisionTolerance, collidesWith);
    }
}
