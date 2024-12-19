package hr.nipeta.kqzero.items;

import hr.nipeta.kqzero.DebugConfig;
import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.entities.Entity;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

public abstract class Item {

    protected GameManager gm;
    public Double worldTileX, worldTileY;
    public final CollisionTolerance collisionTolerance;

    public Item(GameManager gm, Double worldTileX, Double worldTileY) {
        this.gm = gm;
        this.worldTileX = worldTileX;
        this.worldTileY = worldTileY;
        this.collisionTolerance = new CollisionTolerance(0.1d);
    }

    public final void draw() {

        double relativeToPlayerX = gm.player.worldTileX - this.worldTileX;
        double relativeToPlayerY = gm.player.worldTileY - this.worldTileY;

        if (Math.abs(relativeToPlayerX) > (double) gm.world.COLS_TOTAL / 2) {
            return;
        }

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

    public abstract boolean isCollectable(Entity entity);
    public abstract boolean isSolid();

    public abstract String getName();

}
