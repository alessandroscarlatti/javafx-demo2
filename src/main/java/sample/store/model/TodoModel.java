package sample.store.model;

/**
 * @author Alessandro Scarlatti
 * @since Saturday, 5/2/2020
 */
public class TodoModel {
    private String id;
    private String text;
    private boolean completed;

    public TodoModel() {
    }

    public TodoModel(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public TodoModel(TodoModel other) {
        this.id = other.id;
        this.text = other.text;
        this.completed = other.completed;
    }

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
