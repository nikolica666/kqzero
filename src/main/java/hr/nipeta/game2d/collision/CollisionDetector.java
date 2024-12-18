package hr.nipeta.game2d.collision;

import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.world.World;

public interface CollisionDetector {
    boolean check(World world, Entity entity, double tileDistanceTraveled);
}
