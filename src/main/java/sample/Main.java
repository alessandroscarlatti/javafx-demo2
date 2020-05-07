package sample;

import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.AbstractModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
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

        guiceContext.init();

//        Parent root = fxmlLoader.load(getClass().getResourceAsStream("mainView.fxml"));

        Parent root = guiceContext.getInstance(MainView.class);
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
