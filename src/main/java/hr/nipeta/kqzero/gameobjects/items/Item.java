package hr.nipeta.kqzero.gameobjects.items;

import hr.nipeta.kqzero.DebugConfig;
import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.gameobjects.GameObject;
import hr.nipeta.kqzero.gameobjects.entities.Entity;
import hr.nipeta.kqzero.world.tiles.Tile;
import javafx.scene.paint.Color;

public abstract class Item extends GameObject {

    public Item(GameManager gm, Double worldTileX, Double worldTileY) {
        super(gm, worldTileX, worldTileY, new CollisionTolerance(0.1d));
    }

    @Override
    public final void draw() {

        double relativeToPlayerX = gm.player.worldTileX - this.worldTileX;
        if (Math.abs(relativeToPlayerX) > (double) gm.world.COLS_TOTAL / 2) {
            return;
        }

        double relativeToPlayerY = gm.player.worldTileY - this.worldTileY;
        if (Math.abs(relativeToPlayerY) > (double) gm.world.ROWS_TOTAL / 2) {
            return;
        }

        gm.gc.drawImage(
                gm.spriteManager.getItem(this),
                gm.CENTRAL_TILE_TOP_LEFT_X - relativeToPlayerX * gm.TILE_SIZE,
                gm.CENTRAL_TILE_TOP_LEFT_Y - relativeToPlayerY * gm.TILE_SIZE);


        if (DebugConfig.drawEntityCollisionBorder) {
            drawCollisionBorder(relativeToPlayerX, relativeToPlayerY);
        }

    }

    private void drawCollisionBorder(double relativeToPlayerX, double relativeToPlayerY) {

        double topLeftX = gm.CENTRAL_TILE_TOP_LEFT_X - relativeToPlayerX * gm.TILE_SIZE + collisionTolerance.left * gm.TILE_SIZE;
        double topLeftY = gm.CENTRAL_TILE_TOP_LEFT_Y - relativeToPlayerY * gm.TILE_SIZE + collisionTolerance.top * gm.TILE_SIZE;
        double width = collisionTolerance.width * gm.TILE_SIZE;
        double height = collisionTolerance.height * gm.TILE_SIZE;

        gm.gc.setStroke(Color.RED);
        gm.gc.setLineWidth(2);
        gm.gc.strokeRect(topLeftX, topLeftY, width, height);

    }

    public abstract boolean isSpawnableOn(Tile tile);

    public abstract boolean isCollectable(Entity entity);
    public abstract boolean isSolid();

}
