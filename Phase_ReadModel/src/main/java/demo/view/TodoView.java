package demo.view;

import demo.model.Todo;
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

    public TodoView(Todo todo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setController(this);
            fxmlLoader.load(this.getClass().getResourceAsStream("/fxml/TodoView.fxml"));

            // now that the @FXML items have been injected we can use them.
            todoLabel.setText(todo.getText());
            todoCheckBox.setSelected(todo.getCompleted());
        } catch (Exception e) {
            throw new RuntimeException("Error initializing component", e);
        }
    }

    public Parent getView() {
        return view;
    }
}
