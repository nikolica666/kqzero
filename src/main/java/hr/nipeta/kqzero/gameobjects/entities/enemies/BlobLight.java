package hr.nipeta.kqzero.gameobjects.entities.enemies;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.gameobjects.entities.Direction;
import hr.nipeta.kqzero.movement.Movement;
import hr.nipeta.kqzero.movement.collision.ClockwiseCollisionStrategy;
import hr.nipeta.kqzero.movement.traversal.VariableRandomTraversalStrategy;
import hr.nipeta.kqzero.world.tiles.Tile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlobLight extends Enemy {

    public BlobLight(GameManager gameManager, double worldTileX, double worldTileY) {
        super(
                gameManager,
                worldTileX,
                worldTileY,
                new CollisionTolerance(0.3d),
                Tile.water(),
                Movement.builder()
                        .speed(Movement.Speed.constant(2.0d))
                        .direction(Direction.DOWN)
                        .collisionStrategy(new ClockwiseCollisionStrategy())
                        .traversalStrategy(new VariableRandomTraversalStrategy(1.1d, 3.1d))
                        .build()
        );
    }

    @Override
    public String getName() {
        return "Blob Light";
    }
}
