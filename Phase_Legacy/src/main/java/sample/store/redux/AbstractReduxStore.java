package sample.store.redux;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sample.store.action.Reducer;

import java.util.function.BiFunction;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/3/2020
 */
public abstract class AbstractReduxStore<T, A> {

    private ObjectProperty<T> store = new SimpleObjectProperty<>();  // the actual state of this store
    private BiFunction<T, A, T> reducer;  // the reducer to use to compute the next state

    public void dispatch(A action) {
        // compute the next state by calling the reducer
        // passing the current state and the action
        T nextState = reducer.apply(store.get(), action);

        // update the current state.
        // this will call any listeners who are bound to the observable store property
        store.set(nextState);
    }

    public T getStore() {
        return store.get();
    }

    public ObjectProperty<T> storeProperty() {
        return store;
    }

    public void setStore(T store) {
        this.store.set(store);
    }

    public BiFunction<T, A, T>getReducer() {
        return reducer;
    }

    public void setReducer(BiFunction<T, A, T> reducer) {
        this.reducer = reducer;
    }
}
