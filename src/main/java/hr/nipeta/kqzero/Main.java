package hr.nipeta.kqzero;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

@Slf4j
@Getter
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new GameManager().createScene());
        primaryStage.setTitle("King's quest 0");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Fonts.init();
        launch(args);
    }

    public static class Fonts {

        public static Font REGULAR_24;
        public static Font REGULAR_32;
        public static Font REGULAR_48;

        private static void init() {
            REGULAR_24 = loadFont("/fonts/perfect-dos/perfect-dos-vga-437-Regular.ttf", 24);
            REGULAR_32 = loadFont("/fonts/perfect-dos/perfect-dos-vga-437-Regular.ttf", 32);
            REGULAR_48 = loadFont("/fonts/perfect-dos/perfect-dos-vga-437-Regular.ttf", 48);
        }

        private static Font loadFont(String resourceLocalPath, double size) {

            try (InputStream resourceStream = Main.class.getResourceAsStream(resourceLocalPath)) {

                Font font = Font.loadFont(resourceStream, size);

                if (font == null) {
                    log.error("Failed to load font '{}', returning null", resourceLocalPath);
                } else {
                    log.debug("Loaded font '{}' with size {}", resourceLocalPath, size);
                }

                return font;

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return null;
            }

        }

    }

}