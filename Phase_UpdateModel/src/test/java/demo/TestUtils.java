package demo;

import demo.model.Todo;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.util.*;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class TestUtils {

    public static List<Todo> todosSet1() {
        return Arrays.asList(
            todo("todo1", "this is todo1", true),
            todo("todo2", "this is todo2", false),
            todo("todo3", "this is todo3", true),
            todo("todo4", "this is todo4", true),
            todo("todo5", "this is todo5", false),
            todo("todo6", "this is todo6", false),
            todo("todo7", "this is todo7", true),
            todo("todo8", "this is todo8", false)
        );
    }

    public static Todo todo(String id, String text, boolean completed) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setText(text);
        todo.setCompleted(completed);
        return todo;
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
