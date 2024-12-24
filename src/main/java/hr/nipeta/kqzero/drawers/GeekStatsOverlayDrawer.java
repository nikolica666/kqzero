package hr.nipeta.kqzero.drawers;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.Main;
import javafx.scene.paint.Color;

public class GeekStatsOverlayDrawer extends OverlayDrawer {

    public GeekStatsOverlayDrawer(GameManager gm) {
        super(gm);
    }

    @Override
    protected void drawOnEnabled() {

        double textLineHeight = 30;

        gm.gc.setFill(Color.color(0, 0, 0, 0.84d));
        gm.gc.fillRect(5, 5, 500, textLineHeight * 7 + 10);

        gm.gc.setStroke(Color.WHEAT);
        gm.gc.setLineWidth(3d);
        gm.gc.strokeRect(5, 5, 500, textLineHeight * 7 + 10);

        gm.gc.setFont(Main.Fonts.REGULAR_32);
        gm.gc.setFill(Color.WHEAT);

        gm.gc.fillText(String.format("FPS = %s", gm.gameLoop.getFpsCounter().getCurrentFps()), 16, textLineHeight + 4);
        gm.gc.fillText(String.format("Items on map = %s", gm.itemsOnMap.size()), 16, textLineHeight * 2 + 4);
        gm.gc.fillText(String.format("Enemies on map = %s", gm.enemies.size()), 16, textLineHeight * 3 + 4);
        gm.gc.fillText(String.format("Neutrals on map = %s", gm.neutrals.size()), 16, textLineHeight * 4 + 4);

        int tileX = (int)(gm.player.tile.x + gm.player.collisionTolerance.left);
        int tileY = (int)(gm.player.tile.y + gm.player.collisionTolerance.top);

        gm.gc.fillText(String.format("Tile = (%s,%s)", tileX, tileY), 16, textLineHeight * 5 + 4);
        gm.gc.fillText(String.format("Tile type = %s", gm.world.tiles[tileY][tileX]), 16, textLineHeight * 6 + 4);
        gm.gc.fillText(String.format("Tiles traveled = %f", gm.player.getMovement().getTotalDistance()), 16, textLineHeight * 7 + 4);

    }

}
