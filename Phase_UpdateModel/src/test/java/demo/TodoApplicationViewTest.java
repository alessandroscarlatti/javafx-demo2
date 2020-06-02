package demo;

import demo.model.TodoApplication;
import demo.view.TodoApplicationView;
import org.junit.Test;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class TodoApplicationViewTest {

    @Test
    public void test() {
        TodoApplication.getInstance().setTodos(TestUtils.todosSet1());
        TodoApplication.getInstance().getTodoApplicationViewModel().sync();
        TodoApplicationView.show();
    }
}
