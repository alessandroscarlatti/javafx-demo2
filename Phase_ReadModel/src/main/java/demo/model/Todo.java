package demo.model;

/**
 * @author Alessandro Scarlatti
 * @since Sunday, 5/31/2020
 */
public class Todo {

    private String id;
    private String text;
    private boolean completed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
