package hr.nipeta.kqzero.drawers;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.Main;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class HelpOverlayDrawer extends OverlayDrawer {

    private static final List<String> HELP_LINES = Arrays.asList(
            "F1 = help",
            "F3 = geek statistics",
            "W = move up",
            "S = move down",
            "A = move left",
            "D = move right");

    public HelpOverlayDrawer(GameManager gm) {
        super(gm);
    }

    @Override
    protected void drawOnEnabled() {

        int numberOfLines = HELP_LINES.size();
        double textLineHeight = 30;

        double canvasCenterX = gm.gc.getCanvas().getWidth() / 2;

        gm.gc.setFill(Color.color(0, 0, 0, 0.84d));
        gm.gc.fillRect(canvasCenterX - 10, 5, 400, textLineHeight * numberOfLines + 10);

        gm.gc.setStroke(Color.WHEAT);
        gm.gc.setLineWidth(3d);
        gm.gc.strokeRect(canvasCenterX - 10, 5, 400, textLineHeight * numberOfLines + 10);

        gm.gc.setFont(Main.Fonts.REGULAR_32);
        gm.gc.setFill(Color.WHEAT);

        for (int i = 0; i < numberOfLines; i++) {
            gm.gc.fillText(HELP_LINES.get(i), canvasCenterX, textLineHeight * (i + 1) + 4);
        }

    }

}
