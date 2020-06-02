package demo.view;

import demo.model.TodoViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class TodoListView {

    @FXML
    private VBox view;

    @FXML
    private ListView<TodoViewModel> todoList;

    public TodoListView(List<TodoViewModel> todos) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setController(this);
            fxmlLoader.load(this.getClass().getResourceAsStream("/fxml/TodoListView.fxml"));

            todoList.setCellFactory(param -> new CustomListCell<>(item -> new TodoView(item).getView()));

            todoList.getItems().setAll(todos);
            todoList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing component", e);
        }
    }

    public Parent getView() {
        return view;
    }

    private static class CustomListCell<ModelClass> extends ListCell<ModelClass> {
        private Node view;

        private Function<ModelClass, Node> viewSupplier;

        CustomListCell(Function<ModelClass, Node> viewSupplier) {
            this.viewSupplier = viewSupplier;
        }

        @Override
        protected void updateItem(ModelClass item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                if (view == null)
                    view = viewSupplier.apply(item);
                setGraphic(view);
            }
        }
    }
}
