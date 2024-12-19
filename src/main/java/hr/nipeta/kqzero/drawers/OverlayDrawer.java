package hr.nipeta.kqzero.drawers;

import hr.nipeta.kqzero.GameManager;
import lombok.Getter;

@Getter
public abstract class OverlayDrawer extends Drawer {

    private boolean drawEnabled;

    protected OverlayDrawer(GameManager gm) { super(gm); }

    public final void toggleDrawEnabled() {
        drawEnabled = !drawEnabled;
    }

    @Override
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
