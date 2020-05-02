package sample;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

// the key is the actual key that will identify the target object.
// the value is NOT the actual target object, but rather an object
// that provides the definition for the target object.
public class DeclarativeList {

    private List<Object> targetList;  // the list that will updated when the declarative list is synced
    private LinkedHashMap<String, ItemDefinition> currentState = new LinkedHashMap<>();  // the current state of the list
    private LinkedHashMap<String, ItemDefinition> nextState = new LinkedHashMap<>();  // the next state of the list

    public DeclarativeList(List targetList) {
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
                currentItemDefinition.update.accept(nextItemDefinition.getData(), currentItemDefinition.getItem());

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
                itemsToRemove.add(currentStateDefinitionEntry.getValue().getItem());
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
            if (itemDefinition.getItem() != null) {
                // this definition has a target object already created
                // so add it to the index map.
                // if it does not have a target object already created
                // that object will be created later, so it is not needed now.
                indexMap.put(itemDefinition.getItem(), i);
            }
            i++;
        }

        targetList.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return Integer.compare(indexMap.get(o1), indexMap.get(o2));
            }
        });

        // 3. insert new items from the next state in the appropriate position
        int j = 0;
        for (Map.Entry<String, ItemDefinition> actualNextStateEntry : actualNextState.entrySet()) {
            ItemDefinition itemDefinition = actualNextStateEntry.getValue();
            if (itemDefinition.getItem() == null) {
                // this definition does NOT a target object already created
                // so the target object needs to be created, using the factory
                Object item = itemDefinition.getFactory().apply(itemDefinition.getData());

                // add the item to the definition
                itemDefinition.setItem(item);

                // add the item to the target list
                targetList.add(j, item);
            }
            j++;
        }

        // update the current state map
        // and clear the next state so it is ready for the next sync
        currentState = new LinkedHashMap<>(actualNextState);
        nextState.clear();
    }

    public static class ItemDefinition<I, O> {
        private String key;  // the key for this item
        private Object data;  // the definition data that will be used to create the item
        private Function<I, O> factory;  // the function to use when creating the item
        private BiConsumer<I, O> update;  // the function used to update the item
        private Object item;  // the item that is created from this definition

        public ItemDefinition() {
        }

        public ItemDefinition(String key, I data, Function<I, O> factory, BiConsumer<I, O> update) {
            this.key = key;
            this.data = data;
            this.factory = factory;
            this.update = update;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Function<I, O> getFactory() {
            return factory;
        }

        public void setFactory(Function<I, O> factory) {
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

        public BiConsumer<I, O> getUpdate() {
            return update;
        }

        public void setUpdate(BiConsumer<I, O> update) {
            this.update = update;
        }
    }
}