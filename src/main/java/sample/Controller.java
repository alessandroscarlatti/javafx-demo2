package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Controller {

    @FXML
    private TextField nextTodoTextField;

    private List<TodoModel> todos = new ArrayList<>(Arrays.asList(
        new TodoModel("todo1", "todo item 1"),
        new TodoModel("todo2", "todo item 2"),
        new TodoModel("todo3", "todo item 3")
    ));

    private StringProperty nextTodoText = new SimpleStringProperty("todo");

    private int nextTodoId = 4;

    private ObservableList todosFx = FXCollections.observableArrayList();

    private DeclarativeList todosDeclarative;

    // could the "Todo" FX class provide these for us?
    private Function<TodoModel, Todo> todoViewFunction = todoModel -> new Todo(todoModel.getText());
    private BiConsumer<TodoModel, Todo> todoUpdateFunction = (todoModel, todo) -> {
        System.out.println("updating todo " + todo + " with values " + todoModel);
        Syncable.trySync(todo, todoModel);
    };

    public Controller() {
        sync();
    }

    @FXML
    private void initialize() {
        nextTodoTextField.textProperty().bindBidirectional(nextTodoTextProperty());
    }

    public void sync() {

        // React somehow maintains a "magic" reference to this...
        // because it's stored implicitly as the return value of the previous render() function call?
        // But that's just for the root element?  But that's all React cares about anyway.
        // It deals with nested identity as well.
        if (todosDeclarative == null) todosDeclarative = new DeclarativeList(todosFx);

        todosDeclarative.beginSync();

        for (TodoModel todoModel : todos) {
            todosDeclarative.put(new DeclarativeList.ItemDefinition(todoModel.getId(), todoModel, todoViewFunction, todoUpdateFunction));
        }

        todosDeclarative.endSync();
    }

    public void addTodo() {
        todos.add(new TodoModel(String.valueOf(nextTodoId), nextTodoText.get()));
        nextTodoId++;
        sync();
    }

    public void sort() {
        Collections.shuffle(todos);
        sync();
    }

    public void addElipses() {
        for (TodoModel todo : todos) {
            todo.setText(todo.getText() + ".");
        }
        sync();
    }

    public ObservableList getTodosFx() {
        return todosFx;
    }

    public void setTodosFx(ObservableList todosFx) {
        this.todosFx = todosFx;
    }

    public String getNextTodoText() {
        return nextTodoText.get();
    }

    public StringProperty nextTodoTextProperty() {
        return nextTodoText;
    }

    public void setNextTodoText(String nextTodoText) {
        this.nextTodoText.set(nextTodoText);
    }
}
