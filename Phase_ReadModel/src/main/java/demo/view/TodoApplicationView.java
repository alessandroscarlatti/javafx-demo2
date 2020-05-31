package demo.view;

import demo.model.TodoApplication;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class TodoApplicationView extends Application {

    public static void show() {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create the scene
        // creating a scene appears to automatically bind the given node's prefHeight and prefWidth properties
        // to the actual height and width of the application window
        TodoListView todoList = new TodoListView(TodoApplication.getInstance().getTodos());
        Scene scene = new Scene(todoList.getView());

        // init JMetro
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
