package demo;

import demo.model.Todo;
import demo.view.TodoListView;
import javafx.scene.Parent;

import java.util.List;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class TodoListViewTest {

    public static class MultipleTodos extends TestUtils.TestingView {
        @Override
        Parent getView() {
            List<Todo> todos = TestUtils.todosSet1();
            TodoListView todoListView = new TodoListView(todos);
            return todoListView.getView();
        }
    }
}
