package hr.nipeta.game2d;

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
            case P -> gm.gameLoop.toggle();
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
