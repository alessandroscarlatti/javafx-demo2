package sample.view.main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import sample.AppContext;
import sample.DeclarativeList;
import sample.Syncable;
import sample.store.Store;
import sample.store.model.TodoModel;
import sample.utils.FxmlLoader;
import sample.view.todo.TodoView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class MainView2 {

    @FXML
    private Parent view;

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

    // could the "TodoView" FX class provide these for us?
    private Function<TodoModel, TodoView> todoViewFunction = todoModel -> new TodoView(todoModel.getText());
    private BiConsumer<TodoModel, TodoView> todoUpdateFunction = (todoModel, todo) -> {
        System.out.println("updating todo " + todo + " with values " + todoModel);
        Syncable.trySync(todo, todoModel);
    };
    private Function<TodoView, TodoView> todoTargetFunction = todoView -> todoView;

    // call this method to load the view
    // what can we strip out to make things simpler?
    // if we go with the POJOish route...we lose the ability to treat props separately...
    // we could use a naming convention...
    // where a props object could be the props of the view...
    // so a view object could actually be used as a definition...
    public void initialize() {
        FxmlLoader.loadView(this, "mainView.fxml");
        nextTodoTextField.textProperty().bindBidirectional(nextTodoTextProperty());
        sync();
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

    public void addEllipses() {
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

    public Parent getView() {
        return view;
    }
}
