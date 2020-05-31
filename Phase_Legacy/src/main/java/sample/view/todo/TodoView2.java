package sample.view.todo;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import sample.Syncable;
import sample.store.model.TodoModel;
import sample.utils.FXMLUtils;
import sample.utils.FxmlLoader;

/**
 * @author Alessandro Scarlatti
 * @since Thursday, 4/30/2020
 */
public class TodoView2 implements Syncable<TodoModel> {

    private SimpleObjectProperty<String> text = new SimpleObjectProperty<>("text");

    @FXML
    private Parent view;

    @FXML
    private Button todoButton;

    public TodoView2(TodoModel props) {
        FxmlLoader.loadPresenter(this, "todoView2.fxml");
        this.text.set(props.getText());
    }

    @FXML
    private void initialize() {
        todoButton.textProperty().bind(text);
    }

    @Override
    public void sync(TodoModel props) {
        this.text.set(props.getText());
    }

    public void doSomething() {
        System.out.println(text.get());
        setText(getText() + ".");
    }

    public String getText() {
        return text.get();
    }

    public Property<String> textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public Parent getView() {
        return view;
    }

    public void setView(Parent view) {
        this.view = view;
    }
}
