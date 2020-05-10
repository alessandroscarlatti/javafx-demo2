package sample;

import javafx.scene.Node;

import java.util.List;

/**
 * @author Alessandro Scarlatti
 * @since Saturday, 5/9/2020
 */
public abstract class RxView<P> {

    protected P props;

    public void rxUpdateComponent(P nextProps) {
        // update the props
        // but grab the current props so we can use it for the lifecycle method afterward
        P prevProps = props;
        props = nextProps;

        // call the lifecycle method
        componentDidUpdate(prevProps);
    }

    protected void componentDidUpdate(P prevProps) {

    }

    protected abstract List<Node> render();

//    public static <T extends RxView, P> DeclarativeList.ElementDefinition<P, RxView<P>> render(Class<T> clazz, String key, P props) {
//
//        Function<P, RxView<P>> factory = props ->
//
//        return new DeclarativeList.ElementDefinition<P, RxView<P>>(key, props, )
//    }
}
