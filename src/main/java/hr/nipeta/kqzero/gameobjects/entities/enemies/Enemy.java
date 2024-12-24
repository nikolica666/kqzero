package hr.nipeta.kqzero.gameobjects.entities.enemies;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.gameobjects.entities.NonPlayer;
import hr.nipeta.kqzero.movement.Movement;
import hr.nipeta.kqzero.world.WorldTile;
import hr.nipeta.kqzero.world.tiles.Tile;

import java.util.Set;

public abstract class Enemy extends NonPlayer {

    protected Enemy(GameManager gm, WorldTile worldTile, CollisionTolerance collisionTolerance, Set<Tile> collidesWith, Movement movement) {
        super(gm, worldTile, collisionTolerance, collidesWith, movement);
    }

}
