package demo.view;

import demo.TestUtils;
import javafx.scene.Parent;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class TodoListViewTest {

    public static class Test extends TestUtils.TestingView {
        @Override
        protected Parent getView() {
            TodoListView todoListView = new TodoListView(TestUtils.todosSet1());
            return todoListView.getView();
        }
    }
}
