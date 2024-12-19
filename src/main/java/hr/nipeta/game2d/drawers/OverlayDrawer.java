package hr.nipeta.game2d.drawers;

import hr.nipeta.game2d.GameManager;
import lombok.Getter;

public abstract class OverlayDrawer {

    protected final GameManager gm;

    @Getter
    private boolean drawEnabled;

    protected OverlayDrawer(GameManager gm) {
        this.gm = gm;
    }

    public final void toggleDrawEnabled() {
        drawEnabled = !drawEnabled;
    }

    public final void draw() {
        if (drawEnabled) {
            drawOnEnabled();
        }
    }

    /**
     * Put drawing code here, {@link OverlayDrawer} already checks if drawing is enabled
     */
    protected abstract void drawOnEnabled();

}
