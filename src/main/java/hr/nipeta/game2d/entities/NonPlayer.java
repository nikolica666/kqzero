package hr.nipeta.game2d.entities;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.collision.CollisionTolerance;
import hr.nipeta.game2d.world.tiles.Tile;

import java.util.Set;

public abstract class NonPlayer extends Entity {

    protected NonPlayer(GameManager gm, double worldTileX, double worldTileY, double speedTilesPerSecond, CollisionTolerance collisionTolerance, Set<Tile> collidesWith) {
        super(gm, worldTileX, worldTileY, speedTilesPerSecond, collisionTolerance, collidesWith);
    }
}
