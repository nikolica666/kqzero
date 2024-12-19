package hr.nipeta.game2d;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Objects;

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
        loadFonts(32);
        launch(args);
    }

    public static Font lightFont;
    public static Font regularFont;
    public static Font semiBoldFont;
    public static Font boldFont;

//    private static void loadFonts(double size) {
//        lightFont = loadFont("/fonts/lemon-milk/LEMONMILK-Light.otf", size);
//        regularFont = loadFont("/fonts/lemon-milk/LEMONMILK-Regular.otf", size);
//        semiBoldFont = loadFont("/fonts/lemon-milk/LEMONMILK-Medium.otf", size);
//        boldFont = loadFont("/fonts/lemon-milk/LEMONMILK-Bold.otf", size);
//    }

    private static void loadFonts(double size) {
        lightFont = loadFont("/fonts/perfect-dos/perfect-dos-vga-437-Regular.ttf", size);
        regularFont = loadFont("/fonts/perfect-dos/perfect-dos-vga-437-Regular.ttf", size);
        semiBoldFont = loadFont("/fonts/perfect-dos/perfect-dos-vga-437-Regular.ttf", size);
        boldFont = loadFont("/fonts/perfect-dos/perfect-dos-vga-437-Regular.ttf", size);
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