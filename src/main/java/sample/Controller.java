package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Controller {

    private List<TodoModel> todos = new ArrayList<>(Arrays.asList(
        new TodoModel("todo1", "todo item 1"),
        new TodoModel("todo2", "todo item 2"),
        new TodoModel("todo3", "todo item 3")
    ));

    private int nextTodoId = 4;

    private ObservableList todosFx = FXCollections.observableArrayList();

    private DeclarativeList todosDeclarative;

    private Function<TodoModel, Todo> todoViewFunction = todoModel -> new Todo(todoModel.getText());
    private BiConsumer<TodoModel, Todo> todoUpdateFunction = (todoModel, todo) -> System.out.println("updating todo " + todo + " with values " + todoModel);

    public Controller() {
        sync();
    }

    public void sync() {
        if (todosDeclarative == null) todosDeclarative = new DeclarativeList(todosFx);

        todosDeclarative.beginSync();

        for (TodoModel todoModel : todos) {
            todosDeclarative.getNextState().put(todoModel.getId(), new DeclarativeList.ItemDefinition(todoModel.getId(), todoModel, todoViewFunction, todoUpdateFunction));
        }

        todosDeclarative.endSync();
    }

    public void addTodo() {
        todos.add(new TodoModel(String.valueOf(nextTodoId), "todo item " + nextTodoId));
        nextTodoId++;
        sync();
    }

    public ObservableList getTodosFx() {
        return todosFx;
    }

    public void setTodosFx(ObservableList todosFx) {
        this.todosFx = todosFx;
    }

    private static class TodoModel {
        private String id;
        private String text;

        public TodoModel() {
        }

        public TodoModel(String id, String text) {
            this.id = id;
            this.text = text;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
