package hr.nipeta.game2d.collision;

import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.items.Item;
import hr.nipeta.game2d.world.World;
import javafx.geometry.Rectangle2D;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static hr.nipeta.game2d.entities.Entity.Direction.*;
import static java.util.stream.Collectors.toSet;

@Slf4j
@AllArgsConstructor
public class CollisionManager {

    private static final Map<Entity.Direction, CollisionDetector> COLLISION_DETECTORS = Map.of(
            UP, new CollisionDetectorUp(),
            DOWN, new CollisionDetectorDown(),
            LEFT, new CollisionDetectorLeft(),
            RIGHT, new CollisionDetectorRight()
    );

    public boolean check(World world, Entity entity, double tileDistanceTraveled) {
        return COLLISION_DETECTORS
                .get(entity.direction)
                .check(world, entity, tileDistanceTraveled);
    }

    public Set<Item> check(List<Item> itemsOnMap, Entity entity, double tileDistanceTraveled) {

        final double futureEntityRectangleTopLeftDeltaX = switch (entity.direction) {
            case UP, DOWN -> 0;
            case LEFT -> -tileDistanceTraveled;
            case RIGHT -> tileDistanceTraveled;
        };
        final double futureEntityRectangleTopLeftDeltaY = switch (entity.direction) {
            case UP -> -tileDistanceTraveled;
            case DOWN -> tileDistanceTraveled;
            case LEFT, RIGHT -> 0;
        };

        Rectangle2D futureEntityRectangle = new Rectangle2D(
                entity.worldTileX + entity.collisionTolerance.left + futureEntityRectangleTopLeftDeltaX,
                entity.worldTileY + entity.collisionTolerance.top + futureEntityRectangleTopLeftDeltaY,
                1 - entity.collisionTolerance.left - entity.collisionTolerance.right,
                1 - entity.collisionTolerance.top - entity.collisionTolerance.bot);

        return itemsOnMap
                .stream()
                .filter(itemOnMap -> check(itemOnMap, futureEntityRectangle))
                .collect(toSet());

    }

    private boolean check(Item itemOnMap, Rectangle2D futureEntityRectangle) {
        Rectangle2D itemRectangle = new Rectangle2D(itemOnMap.worldTileX, itemOnMap.worldTileY, 1,1);
        return futureEntityRectangle.intersects(itemRectangle);
    }

}
