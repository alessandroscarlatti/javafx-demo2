package sample;

import com.google.inject.Injector;
import sample.store.Store;
import sample.utils.FxmlLoader;

/**
 * @author Alessandro Scarlatti
 * @since Wednesday, 5/6/2020
 */
public class AppContext {

    private FxmlLoader fxmlLoader;
    private Injector injector;
    private Store store;

    private static AppContext instance;

    public static AppContext getInstance() {
        if (instance == null)
            instance = new AppContext();

        return instance;
    }

    public FxmlLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public void setFxmlLoader(FxmlLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public Injector getInjector() {
        return injector;
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
