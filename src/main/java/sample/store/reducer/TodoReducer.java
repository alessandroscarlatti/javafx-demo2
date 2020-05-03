package sample.store.reducer;

import sample.store.action.AbstractAction;
import sample.store.action.Reducer;
import sample.store.action.ToggleTodoAction;
import sample.store.model.TodoModel;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/3/2020
 */
public class TodoReducer implements Reducer<TodoModel> {

    @Override
    public TodoModel reduce(TodoModel todo, AbstractAction action) {
        switch (action.getType()) {
            case TOGGLE_TODO: {
                ToggleTodoAction toggleTodoAction = ((ToggleTodoAction) action);
                if (todo.getId().equals(toggleTodoAction.getId())) {
                    // this action applies to this todo
                    todo = new TodoModel(todo);
                    todo.setCompleted(!todo.getCompleted());
                }
                return todo;
            }
            default:
                return todo;
        }
    }
}
