package sample;

import javafx.scene.Node;

/**
 * @author Alessandro Scarlatti
 * @since Saturday, 5/2/2020
 *
 * An item that can be synced.
 */
public interface Syncable<P> {

    Object SYNCABLE_KEY = new Object();

    default void setSyncable(Node node) {
        // make sure that the given node has a reference to the method to call for sync
        node.getProperties().put(SYNCABLE_KEY, this);
    }

    static void trySync(Object object, Object props) {
        if (object instanceof Node) {
            // this object is a node
            // look for the syncable property, which should be an instance of Syncable
            Syncable syncable = (Syncable) ((Node) object).getProperties().get(SYNCABLE_KEY);
            syncable.sync(props);
        }
    }

    void sync(P props);
}
