package demo;

import demo.view.TodoApplicationView;
import javafx.application.Application;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class Main {


    // so now I want to click the checkbox, and cause that to change the font...
    // also update a text box with the total complete

    public static void main(String[] args) {
        Application.launch(TodoApplicationView.class, args);
    }
}
