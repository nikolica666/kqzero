package hr.nipeta.kqzero.entities.neutral;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.entities.NonPlayer;
import hr.nipeta.kqzero.world.tiles.Tile;

import java.util.Set;

public abstract class Neutral extends NonPlayer {

    protected Neutral(GameManager gm, double worldTileX, double worldTileY, double speedTilesPerSecond, CollisionTolerance collisionTolerance, Set<Tile> collidesWith) {
        super(gm, worldTileX, worldTileY, speedTilesPerSecond, collisionTolerance, collidesWith);
    }

}
