package hr.nipeta.game2d.world;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.world.tiles.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class World {

    private final GameManager gm;

    public int ROWS_TOTAL;
    public int COLS_TOTAL;

    public Tile[][] tiles;

    public World(GameManager gameManager, String mapLocalResource) {

        WorldReader.WorldReaderResult result = new WorldReader().readLocalResource(mapLocalResource);

        this.gm = gameManager;

        this.ROWS_TOTAL = result.getNumberOfRows();
        this.COLS_TOTAL = result.getNumberOfCols();

        tiles = result.getTiles();

    }

    public World(GameManager gameManager, int ROWS_TOTAL, int COLS_TOTAL) {

        this.gm = gameManager;

        this.ROWS_TOTAL = ROWS_TOTAL;
        this.COLS_TOTAL = COLS_TOTAL;

        tiles = new Tile[ROWS_TOTAL][COLS_TOTAL];

    }

    public void initRandom() {

        final Tile[] values = Tile.values();
        final Random random = new Random();

        for (int row = 0; row < ROWS_TOTAL; row++) {
            for (int col = 0; col < COLS_TOTAL; col++) {
                tiles[row][col] = values[random.nextInt(values.length)];
            }
        }

    }

    public void drawCenteredAt(double worldTileX, double worldTileY) {

        GraphicsContext gc = gm.gc;

        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        int worldTileYInt = (int)worldTileY;
        int worldTileXInt = (int)worldTileX;

        double partOfTileY = worldTileY - worldTileYInt;
        double partOfTileX = worldTileX - worldTileXInt;

        int offSetY = worldTileYInt - gm.ROWS_PER_SCREEN / 2;
        int offSetX = worldTileXInt - gm.COLS_PER_SCREEN / 2;

        for (int row = Math.max(offSetY, 0); row < Math.min(offSetY + gm.ROWS_PER_SCREEN + 1, ROWS_TOTAL); row++) {
            for (int col = Math.max(offSetX, 0); col < Math.min(offSetX + gm.COLS_PER_SCREEN + 1, COLS_TOTAL); col++) {

                gc.drawImage(
                        gm.spriteManager.getTile(tiles[row][col]),
                        (col - offSetX - partOfTileX) * gm.TILE_SIZE,
                        (row - offSetY - partOfTileY) * gm.TILE_SIZE);

            }
        }
    }

}
