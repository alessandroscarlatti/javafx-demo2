package sample;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import javafx.fxml.FXMLLoader;
import sample.view.main.MainView;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/3/2020
 */
public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    MainView mainView(FXMLLoader fxmlLoader) {
        return new MainView(fxmlLoader);
    }
}
