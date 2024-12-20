package hr.nipeta.kqzero.gameobjects.entities.enemies;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.movement.Movement;
import hr.nipeta.kqzero.world.tiles.Tile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Scarecrow extends Enemy {

    public Scarecrow(GameManager gm, double worldTileX, double worldTileY) {
        super(gm, worldTileX, worldTileY, new CollisionTolerance(0.3d), Tile.waterOrSolid(), Movement.simple(0.5d, 4.5d));
    }

    @Override
    public String getName() {
        return "Scarecrow";
    }
}
