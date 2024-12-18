package hr.nipeta.game2d;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new GameManager().createScene());
        primaryStage.setTitle("Game 2d");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}