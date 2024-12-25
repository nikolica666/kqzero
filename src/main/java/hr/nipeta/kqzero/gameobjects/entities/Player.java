package hr.nipeta.kqzero.gameobjects.entities;

import hr.nipeta.kqzero.DebugConfig;
import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.SpriteManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.gameobjects.items.Item;
import hr.nipeta.kqzero.movement.Movement;
import hr.nipeta.kqzero.world.WorldTile;
import hr.nipeta.kqzero.world.tiles.Tile;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public final class Player extends Entity {

    private final String name = "Nikola";
    @Getter private final Inventory inventory;

    public Player(GameManager gm, WorldTile worldTile) {
        super(gm, worldTile, new CollisionTolerance(0.35d, 0.15d, 0.3d, 0.3d), Tile.waterOrSolid(), Movement.simple(7, Integer.MAX_VALUE));
        this.inventory = Inventory.empty();
    }

    public boolean addToInventory(Item item) {
        return inventory.addToSlot(item);
    }

    @Override
    public void update(double deltaTimeInSeconds) {

        boolean hasTriedToMove = gm.keyHandler.hasActiveMovementKeys();

        // Track when was last sprite updated
        spriteCounter.addToLastChangeInSeconds(deltaTimeInSeconds);

        if (!hasTriedToMove) {
            return;
        }

        // On purpose, we keep rotating sprites if player is trying to move (even if in collision)
        spriteCounter.incrementCounterIfSpriteChangeNeeded();

        KeyCode lastPressedMovementKey = gm.keyHandler.getLastActiveMovementKey();
        movement.changeDirection(
                switch (lastPressedMovementKey) {
                case W -> Direction.UP;
                case S -> Direction.DOWN;
                case A -> Direction.LEFT;
                case D -> Direction.RIGHT;
                default -> throw new IllegalStateException("Unexpected value: " + lastPressedMovementKey);
        });

        double tileDistanceTraveled = movement.getSpeed().getCurrent() * deltaTimeInSeconds;

        boolean hasCollidedWithTile = gm.collisionManager.check(gm.world, this, tileDistanceTraveled);
        if (hasCollidedWithTile) {
            return;
        }

        // We're covering case where more items can be on same position
        Set<Item> collidedItems = gm.collisionManager.check(gm.itemsOnMap, this, tileDistanceTraveled);

        boolean hasCollidedWithSolidItem = false;

        for (Item collidedItem : collidedItems) {
            collidedItem.triggerBehavior(Entity.Action.MOVE_OVER_ITEM, this);

            if (collidedItem.isSolid()) {
                hasCollidedWithSolidItem = true;
            }
        }

        if (!hasCollidedWithSolidItem) {
            switch (movement.getDirection()) {
                case UP -> tile.y -= tileDistanceTraveled;
                case DOWN -> tile.y += tileDistanceTraveled;
                case LEFT -> tile.x -= tileDistanceTraveled;
                case RIGHT -> tile.x += tileDistanceTraveled;
            }

            // Update (mostly for stats), although this data is important for NPCs
            movement.incrementTotalAndCurrentDirectionDistance(tileDistanceTraveled);

        }

    }

    @Override
    public void draw() {

        // TODO possible optimization? We have to call spriteManager only when sprite will change, not every time...
        SpriteManager.SpriteSheetResult playerSpriteSheet = gm.spriteManager.calculatePlayerSpriteSheet(this.movement.getDirection(), this.spriteCounter.getCount());

        gm.gc.drawImage(
                playerSpriteSheet.getSpriteSheet(),
                playerSpriteSheet.getSource().getMinX(),
                playerSpriteSheet.getSource().getMinY(),
                playerSpriteSheet.getSource().getWidth(),
                playerSpriteSheet.getSource().getHeight(),
                gm.CENTRAL_TILE_TOP_LEFT_X,
                gm.CENTRAL_TILE_TOP_LEFT_Y,
                gm.TILE_SIZE,
                gm.TILE_SIZE);

        if (DebugConfig.drawEntityCollisionBorder) {
            drawCollisionBorder();
        }

    }

    private void drawCollisionBorder() {

        double topLeftX = gm.CENTRAL_TILE_TOP_LEFT_X + collisionTolerance.left * gm.TILE_SIZE;
        double topLeftY = gm.CENTRAL_TILE_TOP_LEFT_Y + collisionTolerance.top * gm.TILE_SIZE;
        double width = collisionTolerance.width * gm.TILE_SIZE;
        double height = collisionTolerance.height * gm.TILE_SIZE;

        gm.gc.setStroke(Color.RED);
        gm.gc.setLineWidth(2);
        gm.gc.strokeRect(topLeftX, topLeftY, width, height);

    }

    @Override
    public String getName() {
        return "Player " + name;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Inventory {

        private final List<ItemSlot> slots;

        private static Inventory empty() {
            return new Inventory(new ArrayList<>());
        }

        public boolean addToSlot(Item item) {
            if (item.getStackStrategy().canStack()) {
                if (addToExistingSlot(item)) {
                    return true;
                } else {
                    return addToNewSlot(item);
                }
            } else {
                return addToNewSlot(item);
            }
        }

        private boolean addToExistingSlot(Item item) {
            // TODO max Inventory capacity
            for (ItemSlot slot : slots) {
                if (slot == null) {
                    continue;
                }
                if (slot.accepts(item) && slot.currentStackSize() < item.getStackStrategy().getMaxStackSize()) {
                    slot.add(item);
                    return true;
                }
            }

            return false;

        }

        private boolean addToNewSlot(Item item) {
            // TODO max Inventory capacity
            ItemSlot newSlot = new ItemSlot();
            newSlot.itemClass = item.getClass();
            newSlot.itemName = item.getName();
            newSlot.items = new ArrayList<>();
            newSlot.items.add(item);
            slots.add(newSlot);
            return true;
        }

        @Getter
        public static class ItemSlot {
            private Class<? extends Item> itemClass;
            private String itemName;
            private List<Item> items;

            public boolean accepts(Item item) {
                return itemName.equals(item.getName()); // TODO equality by name, could be done better maybe
            }

            public int currentStackSize() {
                return items.size();
            }

            public void add(Item item) {
                items.add(item);
            }
        }

    }

}