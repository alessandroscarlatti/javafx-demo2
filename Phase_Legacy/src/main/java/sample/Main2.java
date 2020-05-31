package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import sample.view.main.MainView2;

public class Main2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


//        Parent root = fxmlLoader.load(getClass().getResourceAsStream("mainView.fxml"));

        MainView2 mainView = new MainView2();
        mainView.initialize();
        Parent root = mainView.getView();

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
        launch(Main2.class, args);
    }
}
