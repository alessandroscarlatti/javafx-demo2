package sample;

import sample.utils.FxmlLoader;

/**
 * @author Alessandro Scarlatti
 * @since Wednesday, 5/6/2020
 */
public class AppContext {

    private FxmlLoader fxmlLoader;

    public FxmlLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public void setFxmlLoader(FxmlLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }
}
