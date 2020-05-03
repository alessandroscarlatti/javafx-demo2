package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.getIcons().addAll(
            new Image("/sample/gear/gear-16.png"),
            new Image("/sample/gear/gear-24.png"),
            new Image("/sample/gear/gear-32.png"),
            new Image("/sample/gear/gear-48.png"),
            new Image("/sample/gear/gear-64.png"),
            new Image("/sample/gear/gear-128.png")
        );
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
