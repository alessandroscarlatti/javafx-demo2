package demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Alessandro Scarlatti
 * @since Monday, 6/1/2020
 */
public class TodoApplicationViewModel {

    private ObservableList<TodoViewModel> todoViewModels = FXCollections.observableArrayList();

    public void sync() {
        // create a view model for each todo
        for (Todo todo : TodoApplication.getInstance().getTodos()) {
            TodoViewModel todoViewModel = new TodoViewModel(todo);
            todoViewModels.add(todoViewModel);
        }
    }

    public ObservableList<TodoViewModel> getTodoViewModels() {
        return todoViewModels;
    }

    public void setTodoViewModels(ObservableList<TodoViewModel> todoViewModels) {
        this.todoViewModels = todoViewModels;
    }
}
