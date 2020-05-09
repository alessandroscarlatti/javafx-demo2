package sample;

import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import sample.utils.FxmlLoader;
import sample.view.main.MainView;

import javax.inject.Inject;
import java.util.Arrays;

public class Main extends Application {

    private AbstractModule guiceModule = new GuiceModule();

    private GuiceContext guiceContext = new GuiceContext(this, () -> Arrays.asList(guiceModule));

    @Inject
    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Injector injector = Guice.createInjector(guiceModule);

        AppContext appContext = new AppContext();
        appContext.setInjector(injector);
        FxmlLoader fxmlLoader = new FxmlLoader(clazz -> guiceContext.getInstance(clazz), injector::injectMembers);
        appContext.setFxmlLoader(fxmlLoader);

        guiceContext.init();

//        Parent root = fxmlLoader.load(getClass().getResourceAsStream("mainView.fxml"));

        MainView mainView = new MainView(new MainView.Props(), appContext);
        Parent root = mainView.getView();
//        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/main/mainView.fxml"));

        primaryStage.setTitle("Hello World");
        primaryStage.getIcons().addAll(
            new Image("/sample/gear/gear-16.png"),
            new Image("/sample/gear/gear-24.png"),
            new Image("/sample/gear/gear-32.png"),
            new Image("/sample/gear/gear-48.png"),
            new Image("/sample/gear/gear-64.png"),
            new Image("/sample/gear/gear-128.png")
        );
        Scene scene = new Scene(root);

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(Main.class, args);
    }
}
