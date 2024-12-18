package hr.nipeta.game2d;

import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.entities.neutral.Bird;
import hr.nipeta.game2d.entities.neutral.Fish;
import hr.nipeta.game2d.entities.neutral.Neutral;
import hr.nipeta.game2d.world.tiles.Tile;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

import static hr.nipeta.game2d.SpriteManager.Enemies.*;
import static hr.nipeta.game2d.SpriteManager.Items.*;
import static hr.nipeta.game2d.world.tiles.Tile.*;

public class SpriteManager {

    public enum Enemies { SCARECROW, BLOB_LIGHT }
    public enum Items { COIN, DOOR, KEY}

    private final GameManager gm;

    private final Map<Tile, Image> TILE_SPRITES;
//    private final Map<String, Image> PLAYER_SPRITES;
    private final Image PLAYER_SPRITESHEET;
    private final Map<Enemies, Image> ENEMY_SPRITES;
    private final Map<Class<? extends Neutral>, Image> NEUTRAL_SPRITES;
    private final Map<Items, Image> ITEM_SPRITES;

    public SpriteManager(GameManager gm) {

        this.gm = gm;

        this.TILE_SPRITES = Map.of(
                GRASS_1, resizedSpriteFromResource("/sprites/tiles/grass-1.png"),
                GRASS_2, resizedSpriteFromResource("/sprites/tiles/grass-2.png"),
                SEA_1, resizedSpriteFromResource("/sprites/tiles/sea-1.png"),
                SEA_2, resizedSpriteFromResource("/sprites/tiles/sea-2.png"),
                TREE_1, resizedSpriteFromResource("/sprites/tiles/tree-1.png"),
                TREE_2, resizedSpriteFromResource("/sprites/tiles/tree-2.png")
        );

        this.PLAYER_SPRITESHEET = resizedSpriteSheetFromResource("/sprites/player/spritesheet.png", 4, 4);

        this.ENEMY_SPRITES = Map.of(
                BLOB_LIGHT, resizedSpriteSheetFromResource("/sprites/enemies/blob-spritesheet-4x4.png", 4, 4),
                SCARECROW, resizedSpriteFromResource("/sprites/enemies/cudo-test.png")
        );

        this.NEUTRAL_SPRITES = Map.of(
                Fish.class, resizedSpriteSheetFromResource("/sprites/neutrals/fish-spritesheet-4x4.png", 4, 4),
                Bird.class, resizedSpriteSheetFromResource("/sprites/neutrals/bird-spritesheet-4x4.png", 4,4)
        );

        this.ITEM_SPRITES = Map.of(
                COIN, resizedSpriteFromResource("/sprites/items/coin3.png"),
                KEY, resizedSpriteFromResource("/sprites/items/key2.png"),
                DOOR, resizedSpriteFromResource("/sprites/items/door.png")
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

    public Image getTile(Tile tile) {
        return Objects.requireNonNull(TILE_SPRITES.get(tile));
    }

    public SpriteSheetResult calculateEnemySpriteSheet(Enemies enemy, Entity.Direction direction, int spriteCount) {

        // Player spritesheet is 4 rows (UP,DOWN,LEFT,RIGHT), each has 4 cols of images

        int spriteSheetRow = switch (direction) {
            case UP -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
            case RIGHT -> 3;
        };

        int spriteSheetCol = spriteCount % 4;

        // Display the first sprite (top-left corner)
        Rectangle2D source = new Rectangle2D(spriteSheetCol * gm.TILE_SIZE, spriteSheetRow * gm.TILE_SIZE, gm.TILE_SIZE, gm.TILE_SIZE);

        return new SpriteSheetResult(this.ENEMY_SPRITES.get(enemy), spriteSheetRow, spriteSheetCol, source);

    }

    public SpriteSheetResult calculateNeutralSpriteSheet(Neutral neutral) {

        // Player spritesheet is 4 rows (UP,DOWN,LEFT,RIGHT), each has 4 cols of images

        int spriteSheetRow = switch (neutral.direction) {
            case UP -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
            case RIGHT -> 3;
        };

        int spriteSheetCol = neutral.spriteCounter.counter % 4;

        // Display the first sprite (top-left corner)
        Rectangle2D source = new Rectangle2D(spriteSheetCol * gm.TILE_SIZE, spriteSheetRow * gm.TILE_SIZE, gm.TILE_SIZE, gm.TILE_SIZE);

        return new SpriteSheetResult(this.NEUTRAL_SPRITES.get(neutral.getClass()), spriteSheetRow, spriteSheetCol, source);

    }

    public SpriteSheetResult calculatePlayerSpriteSheet(Entity.Direction direction, int spriteCount) {

        // Player spritesheet is 4 rows (UP,DOWN,LEFT,RIGHT), each has 4 cols of images

        int spriteSheetRow = switch (direction) {
            case UP -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
            case RIGHT -> 3;
        };

        int spriteSheetCol = spriteCount % 4;

        // Display the first sprite (top-left corner)
        Rectangle2D source = new Rectangle2D(spriteSheetCol * gm.TILE_SIZE, spriteSheetRow * gm.TILE_SIZE, gm.TILE_SIZE, gm.TILE_SIZE);

        return new SpriteSheetResult(this.PLAYER_SPRITESHEET, spriteSheetRow, spriteSheetCol, source);

    }

    @AllArgsConstructor
    @Getter
    public static class SpriteSheetResult {
        private Image spriteSheet;
        private int spriteSheetRow;
        private int spriteSheetCol;
        private Rectangle2D source;
    }

    public Image getEnemy(Enemies enemy) {
        return ENEMY_SPRITES.get(enemy);
    }

    public Image getItem(Items item) {
        return ITEM_SPRITES.get(item);
    }

}
