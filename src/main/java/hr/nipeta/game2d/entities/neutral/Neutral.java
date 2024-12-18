package hr.nipeta.game2d.entities.neutral;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.SpriteManager;
import hr.nipeta.game2d.collision.CollisionTolerance;
import hr.nipeta.game2d.entities.NonPlayer;
import hr.nipeta.game2d.world.tiles.Tile;

import java.util.Set;

public abstract class Neutral extends NonPlayer {

    protected Neutral(GameManager gm, double worldTileX, double worldTileY, double speedTilesPerSecond, CollisionTolerance collisionTolerance, Set<Tile> collidesWith) {
        super(gm, worldTileX, worldTileY, speedTilesPerSecond, collisionTolerance, collidesWith);
    }

    public final void draw() {

        double relativeToPlayerX = gm.player.worldTileX - this.worldTileX;
        if (Math.abs(relativeToPlayerX) > (double) gm.world.COLS_TOTAL / 2) {
            return;
        }

        double relativeToPlayerY = gm.player.worldTileY - this.worldTileY;
        if (Math.abs(relativeToPlayerY) > (double) gm.world.ROWS_TOTAL / 2) {
            return;
        }

        // TODO possible optimization? We have to call spriteManager only when sprite will change, not every time...
        SpriteManager.SpriteSheetResult spriteSheetInfo = gm.spriteManager.calculateNeutralSpriteSheet(this);

        gm.gc.drawImage(
                spriteSheetInfo.getSpriteSheet(),
                spriteSheetInfo.getSource().getMinX(),
                spriteSheetInfo.getSource().getMinY(),
                spriteSheetInfo.getSource().getWidth(),
                spriteSheetInfo.getSource().getHeight(),
                gm.CENTRAL_TILE_TOP_LEFT_X - relativeToPlayerX * gm.TILE_SIZE,
                gm.CENTRAL_TILE_TOP_LEFT_Y - relativeToPlayerY * gm.TILE_SIZE,
                gm.TILE_SIZE,
                gm.TILE_SIZE);

    }

}
