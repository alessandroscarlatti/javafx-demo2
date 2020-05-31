package sample;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

// the key is the actual key that will identify the target object.
// the value is NOT the actual target object, but rather an object
// that provides the definition for the target object.
public class DeclarativeList2 {

    private List<Object> targetList;  // the list that will updated when the declarative list is synced
    private LinkedHashMap<String, ItemDefinition> currentState = new LinkedHashMap<>();  // the current state of the list
    private LinkedHashMap<String, ItemDefinition> nextState = new LinkedHashMap<>();  // the next state of the list

    public DeclarativeList2(List targetList) {
        this.targetList = targetList;
    }

    // internal map of things...

    public void beginSync() {
        // clear the next state list
        nextState.clear();
    }

    public void put(ItemDefinition itemDefinition) {
        Objects.requireNonNull(itemDefinition.getKey(), "ItemDefinition key must not be null");
        nextState.put(itemDefinition.getKey(), itemDefinition);
    }

    public void endSync() {
        // synchronize the list with the target
        // uses the current state map to assume what is current
        LinkedHashMap<String, ItemDefinition> actualNextState = new LinkedHashMap<>();

        // ALSO modify the target list using the data from the definition

        for (Map.Entry<String, ItemDefinition> definitionEntry : nextState.entrySet()) {
            // iterate through the nextState definitions in order

            if (currentState.containsKey(definitionEntry.getKey())) {
                // current state already contains this key
                // so 1. Insert into actualNextState from the corresponding value in current state.
                ItemDefinition currentItemDefinition = currentState.get(definitionEntry.getKey());
                ItemDefinition nextItemDefinition = nextState.get(definitionEntry.getKey());
                actualNextState.put(definitionEntry.getKey(), currentItemDefinition);

                // but the actual definition data may be different on this iteration...
                // so we need to call the update function
                // and the target item function
                // so that we update both the item and the target item
                currentItemDefinition.getUpdate().accept(nextItemDefinition.getData(), currentItemDefinition.getItem());
                currentItemDefinition.setTargetItem(currentItemDefinition.getTargetFunction().apply(currentItemDefinition.getItem()));

                // update the data field on the current item definition object
                currentItemDefinition.setData(nextItemDefinition.getData());
            } else {
                // current state does not contain this key
                // so just insert the given definition as is
                actualNextState.put(definitionEntry.getKey(), definitionEntry.getValue());
            }
        }

        // now we have assembled the actualNextState map
        // so now we can begin to synchronize that with the target list

        // 1. remove all items from target that are NOT in the next state

        // collect the items to remove
        Set<Object> itemsToRemove = new HashSet<>();
        for (Map.Entry<String, ItemDefinition> currentStateDefinitionEntry : currentState.entrySet()) {
            if (!actualNextState.containsKey(currentStateDefinitionEntry.getKey())) {
                // this current state item is NOT in the actualNextState, so it needs to be removed
                itemsToRemove.add(currentStateDefinitionEntry.getValue().getTargetItem());
            }
        }

        // now actually remove the items
        targetList.removeIf(itemsToRemove::contains);

        // 2. sort items from target to match the order of the next state

        // create an index map
        Map<Object, Integer> indexMap = new HashMap<>();
        int i = 0;
        for (Map.Entry<String, ItemDefinition> actualNextStateEntry : actualNextState.entrySet()) {
            ItemDefinition itemDefinition = actualNextStateEntry.getValue();
            if (itemDefinition.getTargetItem() != null) {
                // this definition has a target object already created
                // so add it to the index map.
                // if it does not have a target object already created
                // that object will be created later, so it is not needed now.
                indexMap.put(itemDefinition.getTargetItem(), i);
            }
            i++;
        }

        targetList.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                // if there is a npe here, it is because the object in the target list is not in the indexMap
                // this means that the previous step failed to perform properly in some way
                return Integer.compare(indexMap.get(o1), indexMap.get(o2));
            }
        });

        // 3. insert new items from the next state in the appropriate position
        int j = 0;
        for (Map.Entry<String, ItemDefinition> actualNextStateEntry : actualNextState.entrySet()) {
            ItemDefinition itemDefinition = actualNextStateEntry.getValue();
            if (itemDefinition.getTargetItem() == null) {
                // this definition does NOT have a target object already created
                // so the target object needs to be created, using the factory
                Object item = itemDefinition.getFactory().apply(itemDefinition.getData());
                Object targetItem = itemDefinition.getTargetFunction().apply(item);

                // add the item to the definition
                itemDefinition.setItem(item);
                itemDefinition.setTargetItem(targetItem);

                // add the item to the target list
                targetList.add(j, targetItem);
            }

            if (!Objects.equals(targetList.get(j), itemDefinition.getTargetItem())) {
                // always update the target item if it has changed
                targetList.set(j, itemDefinition.getTargetItem());
            }

            j++;
        }

        // update the current state map
        // and clear the next state so it is ready for the next sync
        currentState = new LinkedHashMap<>(actualNextState);
        nextState.clear();
    }

    public static class ItemDefinition<A, B, C> {
        private String key;  // the key for this item
        private Object data;  // the definition data that will be used to create the item
        private Function<A, B> factory;  // the function to use when creating the item
        private BiConsumer<A, B> update;  // the function used to update the item
        private Function<B, C> targetFunction;  // the function to use when getting the target item
        private Object item;  // the item that is created from this definition
        private Object targetItem;  // the object that is extracted from the item

        public ItemDefinition() {
        }

        public ItemDefinition(String key, A data, Function<A, B> factory, BiConsumer<A, B> update, Function<B, C> targetFunction) {
            this.key = key;
            this.data = data;
            this.factory = factory;
            this.update = update;
            this.targetFunction = targetFunction;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Function<A, B> getFactory() {
            return factory;
        }

        public void setFactory(Function<A, B> factory) {
            this.factory = factory;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Object getItem() {
            return item;
        }

        public void setItem(Object item) {
            this.item = item;
        }

        public BiConsumer<A, B> getUpdate() {
            return update;
        }

        public void setUpdate(BiConsumer<A, B> update) {
            this.update = update;
        }

        public Function<B, C> getTargetFunction() {
            return targetFunction;
        }

        public void setTargetFunction(Function<B, C> target) {
            this.targetFunction = target;
        }

        public Object getTargetItem() {
            return targetItem;
        }

        public void setTargetItem(Object targetItem) {
            this.targetItem = targetItem;
        }
    }
}