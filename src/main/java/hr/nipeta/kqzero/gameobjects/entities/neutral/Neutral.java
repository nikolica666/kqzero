package hr.nipeta.kqzero.gameobjects.entities.neutral;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.gameobjects.entities.NonPlayer;
import hr.nipeta.kqzero.movement.Movement;
import hr.nipeta.kqzero.world.tiles.Tile;

import java.util.Set;

public abstract class Neutral extends NonPlayer {

    protected Neutral(GameManager gm, double worldTileX, double worldTileY, CollisionTolerance collisionTolerance, Set<Tile> collidesWith, Movement movement) {
        super(gm, worldTileX, worldTileY, collisionTolerance, collidesWith, movement);
    }

}
