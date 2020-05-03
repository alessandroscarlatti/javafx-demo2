package sample.store.action;

import java.util.function.BiFunction;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/3/2020
 */
public interface Reducer<T> extends BiFunction<T, AbstractAction, T> {

    /**
     * Developer should define the reducer method here.
     * @param state the current state of the slice of state this reducer manages
     * @param action the action dispatched to the store
     * @return the next state for the slice this reducer managers
     */
    T reduce(T state, AbstractAction action);

    @Override
    default T apply(T state, AbstractAction action) {
        return reduce(state, action);
    }
}
