package sample.store.model;

import java.util.List;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/3/2020
 */
public class StoreModel {

    private List<TodoModel> todos;

    public List<TodoModel> getTodos() {
        return todos;
    }

    public void setTodos(List<TodoModel> todos) {
        this.todos = todos;
    }
}
