package demo.view;

import demo.model.Todo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class TodoListView {

    @FXML
    private VBox view;

    @FXML
    private ListView todoList;

    public TodoListView(List<Todo> todos) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setController(this);
            fxmlLoader.load(this.getClass().getResourceAsStream("/fxml/TodoListView.fxml"));

            // now that the @FXML items have been injected we can use them.
            List<Object> todoViews = todos.stream()
                .map(TodoView::new)
                .map(TodoView::getView)
                .collect(Collectors.toList());

            todoList.getItems().setAll(todoViews);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing component", e);
        }
    }

    public Parent getView() {
        return view;
    }
}
