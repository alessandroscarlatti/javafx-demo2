package demo;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class TestUtils {

    public static List<String> todosSet1() {
        return Arrays.asList("todo1", "todo2", "todo3", "todo4", "todo5", "todo6", "todo7", "todo8");
    }

    public static abstract class TestingView extends Application {

        protected abstract Parent getView();

        @Override
        public void start(Stage primaryStage) throws Exception {
            // create the scene
            // creating a scene appears to automatically bind the given node's prefHeight and prefWidth properties
            // to the actual height and width of the application window
            Parent view = getView();
            Scene scene = new Scene(view);

            // init JMetro
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);

            primaryStage.setTitle(this.getClass().getName());
            primaryStage.setScene(scene);

            // show the stage, non-blocking call
            primaryStage.show();
        }
    }
}
