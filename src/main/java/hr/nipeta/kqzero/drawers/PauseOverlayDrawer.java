package hr.nipeta.kqzero.drawers;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.Main;
import javafx.scene.paint.Color;

public class PauseOverlayDrawer extends OverlayDrawer {

    private static final String text = "Game is paused, press P to continue";

    private double textX, textY;

    public PauseOverlayDrawer(GameManager gm) {
        super(gm);

        // TODO check if it is better to use Toolkit.getToolkit().getFontLoader().getFontMetrics()
        javafx.scene.text.Text tempText = new javafx.scene.text.Text(text);
        gm.gc.setFont(Main.Fonts.REGULAR_48); // Need to load Font to get correct lengths
        tempText.setFont(gm.gc.getFont());

        double textWidth = tempText.getLayoutBounds().getWidth();
        double textHeight = tempText.getLayoutBounds().getHeight();

        this.textX = gm.gc.getCanvas().getWidth() / 2 - textWidth / 2;
        this.textY = gm.gc.getCanvas().getHeight() / 2 + textHeight / 4; // Divide by 4 to adjust baseline

    }

    @Override
    protected void drawOnEnabled() {

        gm.gc.setFill(Color.color(0, 0, 0, 0.84d));
        gm.gc.fillRect(0, 0, gm.gc.getCanvas().getWidth(), gm.gc.getCanvas().getHeight());

        gm.gc.setFont(Main.Fonts.REGULAR_48); // Need to load Font to get correct lengths
        gm.gc.setFill(Color.WHEAT);
        gm.gc.fillText(text, textX, textY);

    }

}
