package hr.nipeta.game2d.drawers;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.Main;
import javafx.scene.paint.Color;

public class GeekStatsOverlayDrawer extends OverlayDrawer {

    public GeekStatsOverlayDrawer(GameManager gm) {
        super(gm);
    }

    @Override
    protected void drawOnEnabled() {

        double textLineHeight = 38;

        gm.gc.setFill(Color.color(0, 0, 0, 0.84d));
        gm.gc.fillRect(5, 5, 500, textLineHeight * 6 + 10);

        gm.gc.setStroke(Color.WHEAT);
        gm.gc.setLineWidth(3d);
        gm.gc.strokeRect(5, 5, 500, textLineHeight * 6 + 10);

        gm.gc.setFont(Main.regularFont);
        gm.gc.setFill(Color.WHEAT);

        gm.gc.fillText(String.format("FPS = %s", gm.gameLoop.getFpsCounter().getCurrentFps()), 20, textLineHeight);
        gm.gc.fillText(String.format("Items on map = %s", gm.itemsOnMap.size()), 20, textLineHeight * 2);
        gm.gc.fillText(String.format("Enemies on map = %s", gm.enemies.size()), 20, textLineHeight * 3);
        gm.gc.fillText(String.format("Neutrals on map = %s", gm.neutrals.size()), 20, textLineHeight * 4);

        int tileX = (int)(gm.player.worldTileX + gm.player.collisionTolerance.left);
        int tileY = (int)(gm.player.worldTileY + gm.player.collisionTolerance.top);

        gm.gc.fillText(String.format("Tile = (%s,%s)", tileX, tileY), 20, textLineHeight * 5);
        gm.gc.fillText(String.format("Tile type = %s", gm.world.tiles[tileY][tileX]), 20, textLineHeight * 6);

    }

}
