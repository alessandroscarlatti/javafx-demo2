package sample.view.main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import sample.AppContext;
import sample.BindingUtil;
import sample.DeclarativeList;
import sample.Syncable;
import sample.store.model.TodoModel;
import sample.utils.FxmlLoader;
import sample.view.todo.TodoView;
import sample.view.todo.TodoView2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/3/2020
 */
public class MainView {

    private Props props;
    private AppContext context;

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
    private ObservableList<TodoView2> todosViews = FXCollections.observableArrayList();
    private ObservableList todosFx = FXCollections.observableArrayList();
    private DeclarativeList todosDeclarative;

    // could the "TodoView" FX class provide these for us?
    private Function<TodoModel, TodoView2> todoViewFunction = todoModel -> new TodoView2(todoModel);
    private BiConsumer<TodoModel, TodoView2> todoUpdateFunction = (todoModel, todo) -> {
        System.out.println("updating todo " + todo + " with values " + todoModel);
        todo.sync(todoModel);
    };
    private Function<TodoView2, Parent> todoTargetFunction = todoView -> todoView.getView();

    public MainView(Props props, AppContext context) {
        this.props = props;
        this.context = context;
        FxmlLoader.loadPresenter(this, "mainView.fxml");
        nextTodoTextField.textProperty().bindBidirectional(nextTodoTextProperty());
        todosDeclarative = new DeclarativeList(todosViews);
        BindingUtil.mapContent(todosFx, todosViews, view -> view.getView());
        sync();
    }

    public void sync() {
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

    public static class Props {
        private String defaultText;

        public String getDefaultText() {
            return defaultText;
        }

        public void setDefaultText(String defaultText) {
            this.defaultText = defaultText;
        }
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

    public Props getProps() {
        return props;
    }

    public void setProps(Props props) {
        this.props = props;
    }

    public AppContext getContext() {
        return context;
    }

    public void setContext(AppContext context) {
        this.context = context;
    }

    public Parent getView() {
        return view;
    }

    public void setView(Parent view) {
        this.view = view;
    }
}
