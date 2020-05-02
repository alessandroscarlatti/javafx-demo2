package sample;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.HBox;

/**
 * @author Alessandro Scarlatti
 * @since Thursday, 4/30/2020
 */
public class Todo extends HBox {

    private SimpleObjectProperty<String> text = new SimpleObjectProperty<>("text");

    public Todo() {
        FXMLUtils.loadFXML(this);
    }

    public Todo(String text) {
        this();
        this.text.set(text);
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
