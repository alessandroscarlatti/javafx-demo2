package sample.view.main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import sample.AppContext;
import sample.DeclarativeList;
import sample.Syncable;
import sample.store.Store;
import sample.store.model.TodoModel;
import sample.view.FxmlView;
import sample.view.todo.TodoView;

import javax.inject.Inject;
import javax.naming.Context;
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
public class MainView extends FxmlView {

    public MainView(Props props, AppContext context) {
        context.getFxmlLoader().load(this, props, context);
    }

    public static class Presenter {
        private Props props;
        private Context context;

        @FXML
        private TextField nextTodoTextField;

        private Store store;
        private FXMLLoader fxmlLoader;

        @Inject
        public MainController(FXMLLoader fxmlLoader, Store store) {
            this.store = store;
            this.fxmlLoader = fxmlLoader;
        }

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

        @FXML
        private void initialize() {
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

        public Props getProps() {
            return props;
        }

        public void setProps(Props props) {
            this.props = props;
        }
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
}
