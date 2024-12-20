package hr.nipeta.kqzero;

import hr.nipeta.kqzero.gameobjects.entities.Direction;
import hr.nipeta.kqzero.gameobjects.entities.NonPlayer;
import hr.nipeta.kqzero.gameobjects.entities.enemies.BlobLight;
import hr.nipeta.kqzero.gameobjects.entities.enemies.Scarecrow;
import hr.nipeta.kqzero.gameobjects.entities.neutral.Bird;
import hr.nipeta.kqzero.gameobjects.entities.neutral.Fish;
import hr.nipeta.kqzero.gameobjects.items.*;
import hr.nipeta.kqzero.world.tiles.Tile;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

import static hr.nipeta.kqzero.world.tiles.Tile.*;

public class SpriteManager {

    private final GameManager gm;

    private final Map<Class<? extends NonPlayer>, Image> NON_PLAYER_SPRITES;
    private final Map<Class<? extends Item>, Image> ITEM_SPRITES;
    private final Map<Tile, Image> TILE_SPRITES;
    private final Image PLAYER_SPRITESHEET;

    public SpriteManager(GameManager gm) {

        this.gm = gm;

        this.TILE_SPRITES = Map.of(
                GRASS_1, resizedSpriteFromResource("/sprites/tiles/grass-1.png"),
                GRASS_2, resizedSpriteFromResource("/sprites/tiles/grass-2.png"),
                SEA_1, resizedSpriteFromResource("/sprites/tiles/sea-1.png"),
                SEA_2, resizedSpriteFromResource("/sprites/tiles/sea-2.png"),
                TREE_1, resizedSpriteFromResource("/sprites/tiles/tree-1.png"),
                TREE_2, resizedSpriteFromResource("/sprites/tiles/tree-2.png"),
                ROAD_1, resizedSpriteFromResource("/sprites/tiles/road-1.png"),
                ROAD_2, resizedSpriteFromResource("/sprites/tiles/road-2.png")
        );

        this.PLAYER_SPRITESHEET = resizedSpriteSheetFromResource("/sprites/player/spritesheet.png", 4, 4);

        this.NON_PLAYER_SPRITES = Map.of(
                BlobLight.class, resizedSpriteSheetFromResource("/sprites/enemies/blob-spritesheet-4x4.png", 4, 4),
                Scarecrow.class, resizedSpriteSheetFromResource("/sprites/enemies/scarecrow-spritesheet-4x4.png", 4, 4),
                Fish.class, resizedSpriteSheetFromResource("/sprites/neutrals/fish-spritesheet-4x4.png", 4, 4),
                Bird.class, resizedSpriteSheetFromResource("/sprites/neutrals/bird-spritesheet-4x4.png", 4,4)
        );

        this.ITEM_SPRITES = Map.of(
                Coin.class, resizedSpriteFromResource("/sprites/items/coin3.png"),
                Key.class, resizedSpriteFromResource("/sprites/items/key2.png"),
                Door.class, resizedSpriteFromResource("/sprites/items/door.png"),
                Tree.class, resizedSpriteFromResource("/sprites/items/tree-item-1.png"),
                Pine.class, resizedSpriteFromResource("/sprites/items/pine-item-1.png")
        );

    }

    private Image resizedSpriteSheetFromResource(String localResourcePath, int rows, int cols) {
        try (InputStream inputStream = this.getClass().getResourceAsStream(localResourcePath)) {
            if (inputStream != null) {
                return new Image(inputStream, gm.TILE_SIZE * cols, gm.TILE_SIZE * rows, false, true);
            } else {
                throw new RuntimeException("Image not found: " + localResourcePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load image " + localResourcePath, e);
        }
    }

    private Image resizedSpriteFromResource(String localResourcePath) {
        try (InputStream inputStream = this.getClass().getResourceAsStream(localResourcePath)) {
            if (inputStream != null) {
                return new Image(inputStream, gm.TILE_SIZE, gm.TILE_SIZE, false, true);
            } else {
                throw new RuntimeException("Image not found: " + localResourcePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load image " + localResourcePath, e);
        }
    }

    public SpriteSheetResult calculateNonPlayerEntitySpriteSheet(NonPlayer nonPlayer) {

        // Spritesheet is 4 rows (UP,DOWN,LEFT,RIGHT), each has 4 cols of images

        int spriteSheetRow = switch (nonPlayer.getMovement().getDirection()) {
            case UP -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
            case RIGHT -> 3;
        };

        int spriteSheetCol = nonPlayer.getSpriteCounter().getCount() % 4;

        Rectangle2D source = new Rectangle2D(
                spriteSheetCol * gm.TILE_SIZE,
                spriteSheetRow * gm.TILE_SIZE,
                gm.TILE_SIZE,
                gm.TILE_SIZE);

        return new SpriteSheetResult(NON_PLAYER_SPRITES.get(nonPlayer.getClass()), spriteSheetRow, spriteSheetCol, source);

    }

    public SpriteSheetResult calculatePlayerSpriteSheet(Direction direction, int spriteCount) {

        // Player spritesheet is 4 rows (UP,DOWN,LEFT,RIGHT), each has 4 cols of images

        int spriteSheetRow = switch (direction) {
            case UP -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
            case RIGHT -> 3;
        };

        int spriteSheetCol = spriteCount % 4;

        Rectangle2D source = new Rectangle2D(
                spriteSheetCol * gm.TILE_SIZE,
                spriteSheetRow * gm.TILE_SIZE,
                gm.TILE_SIZE,
                gm.TILE_SIZE);

        return new SpriteSheetResult(PLAYER_SPRITESHEET, spriteSheetRow, spriteSheetCol, source);

    }

    public Image getTile(Tile tile) {
        return Objects.requireNonNull(TILE_SPRITES.get(tile));
    }

    public Image getItem(Item item) {
        return ITEM_SPRITES.get(item.getClass());
    }

    @AllArgsConstructor
    @Getter
    public static class SpriteSheetResult {
        private Image spriteSheet;
        private int spriteSheetRow;
        private int spriteSheetCol;
        private Rectangle2D source;
    }

}
