package sample.store;

import sample.store.action.AbstractAction;
import sample.store.model.StoreModel;
import sample.store.reducer.StoreReducer;
import sample.store.redux.AbstractReduxStore;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/3/2020
 */
public class Store extends AbstractReduxStore<StoreModel, AbstractAction> {

    public Store() {
        setReducer(new StoreReducer());
    }
}
