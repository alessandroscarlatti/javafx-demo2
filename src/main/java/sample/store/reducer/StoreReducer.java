package sample.store.reducer;

import sample.store.action.AbstractAction;
import sample.store.model.StoreModel;
import sample.store.action.Reducer;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/3/2020
 */
public class StoreReducer implements Reducer<StoreModel> {

    private TodosReducer todosReducer = new TodosReducer();

    @Override
    public StoreModel reduce(StoreModel state, AbstractAction action) {
        switch (action.getType()) {
            case INIT:
                state = new StoreModel();
                state.setTodos(todosReducer.apply(null, action));
                return state;
            default:
                state.setTodos(todosReducer.apply(null, action));
                return state;
        }
    }
}
