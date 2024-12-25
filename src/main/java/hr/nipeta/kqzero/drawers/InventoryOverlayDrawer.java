package hr.nipeta.kqzero.drawers;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.Main;
import hr.nipeta.kqzero.gameobjects.entities.Player;
import javafx.scene.paint.Color;

public class InventoryOverlayDrawer extends OverlayDrawer {

    private static final int ROWS = 8;
    private static final int COLS = 8;
    private static final int CELL_MARGIN = 8;
    private static final int SLOT_W = 48 + CELL_MARGIN;
    private static final int SLOT_H = 48 + CELL_MARGIN;

    public InventoryOverlayDrawer(GameManager gm) {
        super(gm);
    }

    @Override
    protected void drawOnEnabled() {

        double canvasCenterX = gm.gc.getCanvas().getWidth() / 2;
        double canvasCenterY = gm.gc.getCanvas().getHeight() / 2;


        gm.gc.setFill(Color.color(0, 0, 0, 0.84d));
        gm.gc.fillRect(canvasCenterX, canvasCenterY, COLS * SLOT_W + CELL_MARGIN, ROWS * SLOT_H + CELL_MARGIN);

        gm.gc.setFont(Main.Fonts.REGULAR_32); // Need to load Font to get correct lengths
        gm.gc.setFill(Color.WHITE);

        gm.gc.setStroke(Color.WHEAT);
        gm.gc.setLineWidth(1d);

        int currentInventorySize = gm.player.getInventory().getSlots().size();

        gm.gc.setFont(Main.Fonts.REGULAR_24);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                gm.gc.strokeRect(
                        canvasCenterX + col * SLOT_W + CELL_MARGIN,
                        canvasCenterY + row * SLOT_H + CELL_MARGIN,
                        SLOT_W - CELL_MARGIN,
                        SLOT_H - CELL_MARGIN);

                int inventoryIndex = col + row * ROWS;
                if (inventoryIndex >= currentInventorySize) {
                    continue;
                }

                Player.Inventory.ItemSlot slot = gm.player.getInventory().getSlots().get(inventoryIndex);
                gm.gc.drawImage(gm.spriteManager.getItem(slot.getItemClass()), canvasCenterX + col * SLOT_W + CELL_MARGIN, canvasCenterY + row * SLOT_H + CELL_MARGIN);

                if (slot.currentStackSize() > 1) {
                    gm.gc.fillText(
                            ""+slot.currentStackSize(),
                            canvasCenterX + col * SLOT_W + CELL_MARGIN + 5,
                            canvasCenterY + row * SLOT_H + SLOT_H - 5);
                }

            }
        }

        gm.gc.setLineWidth(3d);
        gm.gc.strokeRect(canvasCenterX, canvasCenterY, COLS * SLOT_W + CELL_MARGIN,ROWS * SLOT_H + CELL_MARGIN);

    }

}
