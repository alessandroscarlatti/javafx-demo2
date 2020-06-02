package demo;

import demo.model.Todo;
import demo.model.TodoViewModel;
import demo.view.TodoListView;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class TodoListViewTest {

    public static class MultipleTodos extends TestUtils.TestingView {
        @Override
        protected Parent getView() {
            List<Todo> todos = TestUtils.todosSet1();
            List<TodoViewModel> todoViewModels = new ArrayList<>();
            for (Todo todo : todos) {
                todoViewModels.add(new TodoViewModel(todo));
            }

            TodoListView todoListView = new TodoListView(todoViewModels);
            return todoListView.getView();
        }
    }
}
