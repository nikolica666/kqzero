package hr.nipeta.game2d.items;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.SpriteManager;
import hr.nipeta.game2d.entities.Entity;

public class Coin extends Item {

    public Coin(GameManager gm, Double worldTileX, Double worldTileY) {
        super(gm, worldTileX, worldTileY);
    }

    @Override
    public void draw() {

        double relativeToPlayerX = gm.player.worldTileX - this.worldTileX;
        double relativeToPlayerY = gm.player.worldTileY - this.worldTileY;

        if (Math.abs(relativeToPlayerX) > (double) gm.world.COLS_TOTAL / 2) {
            return;
        }

        if (Math.abs(relativeToPlayerY) > (double) gm.world.ROWS_TOTAL / 2) {
            return;
        }

        gm.gc.drawImage(
                gm.spriteManager.getItem(SpriteManager.Items.COIN),
                gm.CENTRAL_TILE_TOP_LEFT_X - relativeToPlayerX * gm.TILE_SIZE,
                gm.CENTRAL_TILE_TOP_LEFT_Y - relativeToPlayerY * gm.TILE_SIZE);

    }

    @Override
    public boolean isCollectable(Entity entity) {
        return true;
    }

}
