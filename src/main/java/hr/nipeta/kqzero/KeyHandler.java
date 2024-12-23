package hr.nipeta.kqzero;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class KeyHandler implements EventHandler<KeyEvent> {

    private final GameManager gm;

    private final List<KeyCode> activeMovementKeys = new ArrayList<>();

    public KeyHandler(GameManager gameManager) {
        this.gm = gameManager;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getEventType().getName()) {
            case "KEY_PRESSED" -> handleKeyPress(event);
            case "KEY_RELEASED" -> handleKeyRelease(event);
        }
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W,A,S,D -> activeMovementKeys.add(event.getCode());
            // TODO Refactoring needed most likely...
            case P -> {
                gm.pauseDrawer.toggleDrawEnabled();
                gm.gameLoop.toggleLoopPlay();
                if (!gm.gameLoop.isPlaying()) {
                    gm.pauseDrawer.draw();
                }
            }
            case F1 -> {
                gm.helpOverlayDrawer.toggleDrawEnabled();
                // TODO this should be moved somewhere else probably... we're only covering case when game is paused
                if (!gm.gameLoop.isPlaying()) {
                    // Draw popup only
                    if (gm.helpOverlayDrawer.isDrawEnabled()) {
                        gm.helpOverlayDrawer.draw();
                    }
                    // Redraw everything to remove popup (to cover game loop stopped scenario)
                    else {
                        gm.draw(); // TODO with secondary canvas we can optimize (no need to redraw all then)
                    }
                }

            }
            case F3 -> {
                gm.geekStatsOverlayDrawer.toggleDrawEnabled();
                // TODO this should be moved somewhere else probably... we're only covering case when game is paused
                if (!gm.gameLoop.isPlaying()) {
                    // Draw popup only
                    if (gm.geekStatsOverlayDrawer.isDrawEnabled()) {
                        gm.geekStatsOverlayDrawer.draw();
                    }
                    // Redraw everything to remove popup (to cover game loop stopped scenario)
                    else {
                        gm.draw(); // TODO with secondary canvas we can optimize (no need to redraw all then)
                    }
                }
            }
        }
    }

    private void handleKeyRelease(KeyEvent event) {
        switch (event.getCode()) {
            case W,A,S,D -> activeMovementKeys.removeIf(e -> e == event.getCode());
        }
    }

    public boolean hasActiveMovementKeys() {
        return !activeMovementKeys.isEmpty();
    }

    public KeyCode getLastActiveMovementKey() {
        return activeMovementKeys.getLast();
    }
}
