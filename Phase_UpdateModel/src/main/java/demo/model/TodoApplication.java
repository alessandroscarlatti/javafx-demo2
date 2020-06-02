package demo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class TodoApplication {

    private static TodoApplication instance = new TodoApplication();
    private List<Todo> todos = new ArrayList<>();
    private TodoApplicationViewModel todoApplicationViewModel = new TodoApplicationViewModel();

    public static TodoApplication getInstance() {
        return instance;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public TodoApplicationViewModel getTodoApplicationViewModel() {
        return todoApplicationViewModel;
    }

    public void setTodoApplicationViewModel(TodoApplicationViewModel todoApplicationViewModel) {
        this.todoApplicationViewModel = todoApplicationViewModel;
    }
}
