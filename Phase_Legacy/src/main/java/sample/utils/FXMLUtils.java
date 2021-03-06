package sample.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * @author Alessandro Scarlatti
 * @since Thursday, 4/30/2020
 */
public class FXMLUtils {

    public static <T extends Parent> void loadFXML(FXMLLoader fxmlLoader, T component) {
        try {
            String fileName = component.getClass().getSimpleName() + ".fxml";
            fxmlLoader.setRoot(component);
            fxmlLoader.load(component.getClass().getResourceAsStream(fileName));
        } catch (Exception e) {
            throw new RuntimeException("Error loading fxml for component " + component, e);
        }
    }

    public static <T extends Parent> void loadFXML(T component) {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(component);
        loader.setControllerFactory(theClass -> component);

        String fileName = component.getClass().getSimpleName() + ".fxml";
        try {
            loader.load(component.getClass().getResourceAsStream(fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
