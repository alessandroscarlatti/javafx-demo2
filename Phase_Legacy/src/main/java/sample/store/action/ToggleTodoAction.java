package sample.store.action;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/3/2020
 */
public class ToggleTodoAction extends AbstractAction {

    private String id;

    @Override
    public ActionType getType() {
        return ActionType.TOGGLE_TODO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
