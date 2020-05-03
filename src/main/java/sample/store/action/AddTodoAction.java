package sample.store.action;

import sample.store.model.TodoModel;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/3/2020
 */
public class AddTodoAction extends AbstractAction {

    private TodoModel todo;

    @Override
    public ActionType getType() {
        return ActionType.ADD_TODO;
    }

    public TodoModel getTodo() {
        return todo;
    }

    public void setTodo(TodoModel todo) {
        this.todo = todo;
    }
}
