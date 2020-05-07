package sample.view.todo;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import sample.utils.FXMLUtils;
import sample.Syncable;
import sample.store.model.TodoModel;

/**
 * @author Alessandro Scarlatti
 * @since Thursday, 4/30/2020
 */
public class TodoView extends HBox implements Syncable<TodoModel> {

    private SimpleObjectProperty<String> text = new SimpleObjectProperty<>("text");

    @FXML
    private Button todoButton;

    public TodoView() {
        FXMLUtils.loadFXML(this);
    }

    public TodoView(String text) {
        this();
        this.text.set(text);
        setSyncable(this);
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
}
