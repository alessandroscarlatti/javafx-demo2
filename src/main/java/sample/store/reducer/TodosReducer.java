package sample.store.reducer;

import sample.store.action.AbstractAction;
import sample.store.action.AddTodoAction;
import sample.store.model.TodoModel;
import sample.store.action.Reducer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/3/2020
 */
public class TodosReducer implements Reducer<List<TodoModel>> {

    private TodoReducer todoReducer = new TodoReducer();

    @Override
    public List<TodoModel> reduce(List<TodoModel> state, AbstractAction action) {
        switch(action.getType()) {
            case INIT:
                return new ArrayList<>();
            case ADD_TODO: {
                // add a todo to the list
                AddTodoAction addTodoAction = ((AddTodoAction) action);
                state = new ArrayList<>(state);
                state.add(addTodoAction.getTodo());
                state.forEach(todo -> todoReducer.apply(todo, action));
                return state;
            }
            default:
                state.forEach(todo -> todoReducer.apply(todo, action));
                return state;
        }
    }
}
