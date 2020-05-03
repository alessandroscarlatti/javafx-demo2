package sample;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * @author Alessandro Scarlatti
 * @since Thursday, 4/30/2020
 */
public class Todo extends HBox implements Syncable<TodoModel> {

    private SimpleObjectProperty<String> text = new SimpleObjectProperty<>("text");

    @FXML
    private Button todoButton;

    public Todo() {
        FXMLUtils.loadFXML(this);
    }

    public Todo(String text) {
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
