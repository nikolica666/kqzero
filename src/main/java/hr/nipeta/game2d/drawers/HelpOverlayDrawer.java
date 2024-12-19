package hr.nipeta.game2d.drawers;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.Main;
import javafx.scene.paint.Color;

public class HelpOverlayDrawer extends OverlayDrawer {

    public HelpOverlayDrawer(GameManager gm) {
        super(gm);
    }

    @Override
    protected void drawOnEnabled() {

        double textLineHeight = 38;

        double canvasCenterX = gm.gc.getCanvas().getWidth() / 2;

        gm.gc.setFill(Color.color(0, 0, 0, 0.84d));
        gm.gc.fillRect(canvasCenterX - 10, 5, 400, textLineHeight * 6 + 10);

        gm.gc.setStroke(Color.WHEAT);
        gm.gc.setLineWidth(3d);
        gm.gc.strokeRect(canvasCenterX - 10, 5, 400, textLineHeight * 6 + 10);

        gm.gc.setFont(Main.regularFont);
        gm.gc.setFill(Color.WHEAT);

        gm.gc.fillText("F1 = help", canvasCenterX, textLineHeight * 1);
        gm.gc.fillText("F3 = geek statistics", canvasCenterX, textLineHeight * 2);
        gm.gc.fillText("W = move up", canvasCenterX, textLineHeight * 3);
        gm.gc.fillText("S = move down", canvasCenterX, textLineHeight * 4);
        gm.gc.fillText("A = move left", canvasCenterX, textLineHeight * 5);
        gm.gc.fillText("D = move right", canvasCenterX, textLineHeight * 6);

    }

}
