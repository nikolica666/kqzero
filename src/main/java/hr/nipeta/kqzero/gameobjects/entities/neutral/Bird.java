package hr.nipeta.kqzero.gameobjects.entities.neutral;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.movement.Movement;
import hr.nipeta.kqzero.world.WorldTile;

import java.util.Collections;
import java.util.Random;

public class Bird extends Neutral {

    public Bird(GameManager gm, WorldTile worldTile) {
        super(gm, worldTile, new CollisionTolerance(0.4d), Collections.emptySet(), Movement.simple(new Random().nextInt(3,8), 7));
    }

    @Override
    public String getName() {
        return "Bird";
    }
}
