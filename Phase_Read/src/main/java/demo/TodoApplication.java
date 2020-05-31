package demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class TodoApplication extends Application {

    public static void main(String[] args) {
        Application.launch(TodoApplication.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create the scene
        // and init JMetro
        Scene scene = new Scene(new Pane());
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        primaryStage.setTitle("Todo Application");
        primaryStage.getIcons().addAll(
            new Image("/demo/gear/gear-16.png"),
            new Image("/demo/gear/gear-24.png"),
            new Image("/demo/gear/gear-32.png"),
            new Image("/demo/gear/gear-48.png"),
            new Image("/demo/gear/gear-64.png"),
            new Image("/demo/gear/gear-128.png")
        );
        primaryStage.setScene(scene);

        // show the stage, non-blocking call
        primaryStage.show();
    }
}
