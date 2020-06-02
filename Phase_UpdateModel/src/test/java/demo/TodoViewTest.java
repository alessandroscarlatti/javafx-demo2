package demo;

import demo.model.TodoViewModel;
import demo.view.TodoView;
import javafx.scene.Parent;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class TodoViewTest {

    public static class CheckedTodo extends TestUtils.TestingView {
        @Override
        protected Parent getView() {
            TodoView todoView = new TodoView(new TodoViewModel(TestUtils.todo("todo1", "this is todo1", true)));
            return todoView.getView();
        }
    }

    public static class UncheckedTodo extends TestUtils.TestingView {
        @Override
        protected Parent getView() {
            TodoView todoView = new TodoView(new TodoViewModel(TestUtils.todo("todo1", "this is todo1", false)));
            return todoView.getView();
        }
    }
}
