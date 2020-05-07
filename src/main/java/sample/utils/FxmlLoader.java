package sample.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.function.Function;

/**
 * @author Alessandro Scarlatti
 * @since Wednesday, 5/6/2020
 */
public class FxmlLoader {

    private Function<Class<?>, Object> controllerFactory;

    public FxmlLoader() {
    }

    public FxmlLoader(Function<Class<?>, Object> controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    private static Object instantiateController(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error instantiating controller class " + clazz, e);
        }
    }

    /**
     * Load the view from fxml by convention.
     * @param view the view object that is being loaded
     * @param props the props object for this view
     * @param context the context object for this view
     * @return the view object, containing both the ui component and the presenter
     */
    public Object load(Object view, Object props, Object context) {
        try {
            // get a new fxml loader for this component
            FXMLLoader fxmlLoader = new FXMLLoader();

            if (controllerFactory != null) {
                fxmlLoader.setControllerFactory(param -> {
                    // ask the factory for the initial instance of the controller
                    Object controller = null;
                    if (controllerFactory != null) {
                        // controller factory provided, so use it
                        controller = controllerFactory.apply(param);
                    } else {
                        // controller factory not provided, use default factory
                        controller = instantiateController(param);
                    }

                    // now optionally inject the props and context
                    optionallyInjectProperty(controller, "props", props);
                    optionallyInjectProperty(controller, "context", context);

                    return controller;
                });
            }

            // determine the fxml file and load it
            String fxmlFile = view.getClass().getSimpleName() + ".fxml";
            Parent parent = fxmlLoader.load(this.getClass().getResourceAsStream(fxmlFile));

            // optionally inject view and presenter
            optionallyInjectProperty(view, "view", parent);
            optionallyInjectProperty(view, "presenter", fxmlLoader.getController());

            // return the view that has now been loaded
            return view;
        } catch (Exception e) {
            throw new RuntimeException("Error loading fxml for view " + view, e);
        }
    }

    private static void optionallyInjectProperty(Object bean, String property, Object value) {
        try {
            if (PropertyUtils.isWriteable(bean, property)) {
                PropertyUtils.setProperty(bean, property, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error optionally inject bean property " + property, e);
        }
    }
}
