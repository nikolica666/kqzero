package hr.nipeta.game2d.entities;

import hr.nipeta.game2d.DebugConfig;
import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.SpriteManager;
import hr.nipeta.game2d.collision.CollisionTolerance;
import hr.nipeta.game2d.items.Item;
import hr.nipeta.game2d.world.tiles.Tile;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public final class Player extends Entity {

    private final List<Item> inventory;

    public Player(GameManager gm, int worldTileX, int worldTileY) {
        super(gm, worldTileX, worldTileY,7,
                new CollisionTolerance(0.35d, 0.15d, 0.3d,0.3d),
                Tile.waterOrSolid());
        this.inventory = new ArrayList<>();
    }

    @Override
    public void update(double deltaTimeInSeconds) {

        boolean hasTriedToMove = gm.keyHandler.hasActiveMovementKeys();

        // Track when was last sprite updated
        spriteCounter.addToLastChangeInSeconds(deltaTimeInSeconds);

        if (hasTriedToMove) {

            // On purpose, we keep rotating sprites if player is trying to move (even if in collision)
            // Set sprite index if needed, so draw() will switch
            spriteCounter.incrementCounterIfSpriteChangeNeeded();

            KeyCode lastPressedMovementKey = gm.keyHandler.getLastActiveMovementKey();
            direction = switch (lastPressedMovementKey) {
                case W -> Entity.Direction.UP;
                case S -> Entity.Direction.DOWN;
                case A -> Entity.Direction.LEFT;
                case D -> Entity.Direction.RIGHT;
                default -> throw new IllegalStateException("Unexpected value: " + lastPressedMovementKey);
            };

            double tileDistanceTraveled = speedTilesPerSecond * deltaTimeInSeconds;

            boolean hasCollidedWithTile = gm.collisionManager.check(gm.world, this, tileDistanceTraveled);
            if (!hasCollidedWithTile) {

                boolean hasCollidedWithSolidItem = false;

                // We're covering case where more items can be on same position
                Set<Item> collidedItems = gm.collisionManager.check(gm.itemsOnMap, this, tileDistanceTraveled);
                for (Item collidedItem : collidedItems) {
                    if (collidedItem.isCollectable(this)) {
                        gm.itemsOnMap.remove(collidedItem);
                        inventory.add(collidedItem);
                        log.debug("My inventory has {} items, and map has {}!", inventory.size(), gm.itemsOnMap.size());
                    }
                    if (collidedItem.isSolid()) {
                        hasCollidedWithSolidItem = true;
                        log.debug("hasCollidedWithSolidItem!");
                    }
                }

                if (!hasCollidedWithSolidItem) {
                    switch (direction) {
                        case UP -> worldTileY -= tileDistanceTraveled;
                        case DOWN -> worldTileY += tileDistanceTraveled;
                        case LEFT -> worldTileX -= tileDistanceTraveled;
                        case RIGHT -> worldTileX += tileDistanceTraveled;
                    }
                }

            }

        }

    }

    @Override
    public void draw() {

        // TODO possible optimization? We have to call spriteManager only when sprite will change, not every time...
        SpriteManager.SpriteSheetResult playerSpriteSheet = gm.spriteManager.calculatePlayerSpriteSheet(this.direction, this.spriteCounter.getCount());

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
        double width = (1 - collisionTolerance.left - collisionTolerance.right) * gm.TILE_SIZE;
        double height = (1 - collisionTolerance.top - collisionTolerance.bot) * gm.TILE_SIZE;

        gm.gc.setStroke(Color.RED);
        gm.gc.setLineWidth(2);
        gm.gc.strokeRect(topLeftX, topLeftY, width, height);

    }

}
