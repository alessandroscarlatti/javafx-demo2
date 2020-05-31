package demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
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
        // scene will contain only one label
        Pane pane = new Pane();
        Label label = new Label("Todo Application");
        label.setFont(new Font(label.getFont().getName(), 72.0));
        pane.getChildren().add(label);
        Scene scene = new Scene(pane);

        // init JMetro
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        primaryStage.setTitle("Todo Application");
        primaryStage.getIcons().addAll(
            new Image("/images/gear/gear-16.png"),
            new Image("/images/gear/gear-24.png"),
            new Image("/images/gear/gear-32.png"),
            new Image("/images/gear/gear-48.png"),
            new Image("/images/gear/gear-64.png"),
            new Image("/images/gear/gear-128.png")
        );
        primaryStage.setScene(scene);

        // show the stage, non-blocking call
        primaryStage.show();
    }
}
