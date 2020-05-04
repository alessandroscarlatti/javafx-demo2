package sample.view.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import sample.FXMLUtils;

import javax.inject.Inject;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/3/2020
 */
public class MainView extends VBox {

    @Inject
    public MainView(FXMLLoader fxmlLoader) {
        FXMLUtils.loadFXML(fxmlLoader, this);
    }
}
