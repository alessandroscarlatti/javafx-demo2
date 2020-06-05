package demo.view;

import demo.model.Todo;
import demo.model.TodoViewModel;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class TodoView {

    @FXML
    private Parent view;

    @FXML
    private Label todoLabel;

    @FXML
    private CheckBox todoCheckBox;

    public TodoView(TodoViewModel todo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setController(this);
            fxmlLoader.load(this.getClass().getResourceAsStream("/fxml/TodoView.fxml"));

            // now that the @FXML items have been injected we can use them.
            todoLabel.setText(todo.getText());
            todoCheckBox.setSelected(todo.isCompleted());

            todoCheckBox.selectedProperty().bindBidirectional(todo.completedProperty());

            todoCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("todo " + todo.getId() + " set completed to " + newValue);
            });


            todoLabel.styleProperty().bind(Bindings.createObjectBinding(() -> {
                if (todo.isCompleted()) {
                    // completed
                    return "-fx-text-fill: gray;";
                } else {
                    // not completed
                    return "-fx-text-fill: blue;";
                }
            }, todo.completedProperty()));


        } catch (Exception e) {
            throw new RuntimeException("Error initializing component", e);
        }
    }

    public Parent getView() {
        return view;
    }
}
