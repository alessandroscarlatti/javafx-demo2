package demo.view;

import demo.model.TodoViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 *
 * The todo item itself, rendered inside the todo list
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

            // a label shows the todo text
            todoLabel.setText(todo.getText());

            // a checkbox shows the state of the todo
            todoCheckBox.setSelected(todo.isCompleted());

            // the checkbox and the view model are bound together
            todoCheckBox.selectedProperty().bindBidirectional(todo.completedProperty());

            todoCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                // when the checkbox is changed we respond
                System.out.println("todo " + todo.getId() + " set completed to " + newValue);
            });

            // the color of the text is derived from the state of the todo
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
