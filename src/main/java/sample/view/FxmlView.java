package sample.view;

/**
 * @author Alessandro Scarlatti
 * @since Wednesday, 5/6/2020
 */
public class FxmlView {

    private Object presenter;
    private Object view;

    @SuppressWarnings("unchecked")
    private <T> T getPresenter() {
        return (T) presenter;
    }

    @SuppressWarnings("unchecked")
    private <T> T getView() {
        return (T) view;
    }

    public void setPresenter(Object presenter) {
        this.presenter = presenter;
    }

    public void setView(Object view) {
        this.view = view;
    }
}
