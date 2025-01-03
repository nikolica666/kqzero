package hr.nipeta.kqzero.collision;

import hr.nipeta.kqzero.gameobjects.entities.Direction;
import hr.nipeta.kqzero.gameobjects.entities.Entity;
import hr.nipeta.kqzero.gameobjects.items.Item;
import hr.nipeta.kqzero.world.World;
import javafx.geometry.Rectangle2D;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static hr.nipeta.kqzero.gameobjects.entities.Direction.*;
import static java.util.stream.Collectors.toSet;

@Slf4j
@AllArgsConstructor
public class CollisionManager {

    private static final Map<Direction, CollisionDetector> COLLISION_DETECTORS = Map.of(
            UP, new CollisionDetectorUp(),
            DOWN, new CollisionDetectorDown(),
            LEFT, new CollisionDetectorLeft(),
            RIGHT, new CollisionDetectorRight()
    );

    public boolean check(World world, Entity entity, double tileDistanceTraveled) {
        return COLLISION_DETECTORS
                .get(entity.getMovement().getDirection())
                .check(world, entity, tileDistanceTraveled);
    }

    public Set<Item> check(Collection<Item> itemsOnMap, Entity entity, double tileDistanceTraveled) {

        Direction direction = entity.getMovement().getDirection();

        final double futureEntityRectangleTopLeftDeltaX = distanceTraveledX(direction, tileDistanceTraveled);
        final double futureEntityRectangleTopLeftDeltaY = distanceTraveledY(direction, tileDistanceTraveled);

        Rectangle2D futureEntityRectangle = new Rectangle2D(
                entity.tile.x + entity.collisionTolerance.left + futureEntityRectangleTopLeftDeltaX,
                entity.tile.y + entity.collisionTolerance.top + futureEntityRectangleTopLeftDeltaY,
                entity.collisionTolerance.width,
                entity.collisionTolerance.height);

        return itemsOnMap
                .stream()
                .filter(itemOnMap -> check(itemOnMap, futureEntityRectangle))
                .collect(toSet());

    }

    private double distanceTraveledX(Direction direction, double distanceTraveled) {
        return switch (direction) {
            case UP, DOWN -> 0;
            case LEFT -> -distanceTraveled;
            case RIGHT -> distanceTraveled;
        };
    }

    private double distanceTraveledY(Direction direction, double distanceTraveled) {
        return switch (direction) {
            case UP -> -distanceTraveled;
            case DOWN -> distanceTraveled;
            case LEFT, RIGHT -> 0;
        };
    }

    private boolean check(Item itemOnMap, Rectangle2D futureEntityRectangle) {
        Rectangle2D itemRectangle = new Rectangle2D(
                itemOnMap.tile.x + itemOnMap.collisionTolerance.left,
                itemOnMap.tile.y + itemOnMap.collisionTolerance.top,
                itemOnMap.collisionTolerance.width,
                itemOnMap.collisionTolerance.height);
        return futureEntityRectangle.intersects(itemRectangle);
    }

}