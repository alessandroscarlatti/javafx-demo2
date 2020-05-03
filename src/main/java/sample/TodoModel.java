package sample;

/**
 * @author Alessandro Scarlatti
 * @since Saturday, 5/2/2020
 */
public class TodoModel {
    private String id;
    private String text;

    public TodoModel() {
    }

    public TodoModel(String id, String text) {
        this.id = id;
        this.text = text;
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
}
