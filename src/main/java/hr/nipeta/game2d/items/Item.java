package hr.nipeta.game2d.items;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.entities.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item {

    protected GameManager gm;

    public Double worldTileX, worldTileY;

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

    }

    public abstract boolean isCollectable(Entity entity);
    public abstract boolean isSolid();

}
