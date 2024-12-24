package hr.nipeta.kqzero.gameobjects.entities.neutral;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.movement.Movement;
import hr.nipeta.kqzero.world.WorldTile;
import hr.nipeta.kqzero.world.tiles.Tile;

public class Fish extends Neutral {

    public Fish(GameManager gameManager, WorldTile worldTile) {
        super(gameManager, worldTile, new CollisionTolerance(.1), Tile.nonWater(), Movement.simple(2.5d, 1.5d));
    }

    @Override
    public String getName() {
        return "Fish";
    }
}
