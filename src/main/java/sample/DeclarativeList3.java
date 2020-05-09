package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

// the key is the actual key that will identify the target object.
// the value is NOT the actual target object, but rather an object
// that provides the definition for the target object.
public class DeclarativeList3 {

    private List<Object> targetList;  // the list that will updated when the declarative list is synced
    private ObservableList<ItemDefinition> itemDefinitionsList;  // the list that contains the unmapped items that will be mapped to the target list
    private LinkedHashMap<String, ItemDefinition> currentState = new LinkedHashMap<>();  // the current state of the list
    private LinkedHashMap<String, ItemDefinition> nextState = new LinkedHashMap<>();  // the next state of the list

    public DeclarativeList3(List targetList) {
        this.targetList = targetList;
        this.itemDefinitionsList = FXCollections.observableArrayList();

        // bind the itemDefinitionsList to the target list
        BindingUtil.mapContent(targetList, itemDefinitionsList, item -> {
            Object mappedItem = item.getItemMapper().apply(item.getItem());
            item.setMappedItem(mappedItem);
            return mappedItem;
        });
    }

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
                // this current state item definition is NOT in the actualNextState, so it needs to be removed
                itemsToRemove.add(currentStateDefinitionEntry.getValue());
            }
        }

        // now actually remove the items
        itemDefinitionsList.removeIf(itemsToRemove::contains);

        // 2. sort items from target to match the order of the next state

        // create an index map
        Map<Object, Integer> indexMap = new HashMap<>();
        int i = 0;
        for (Map.Entry<String, ItemDefinition> actualNextStateEntry : actualNextState.entrySet()) {
            ItemDefinition itemDefinition = actualNextStateEntry.getValue();
            if (itemDefinition != null) {
                // this definition has a target object already created
                // so add it to the index map.
                // if it does not have a target object already created
                // that object will be created later, so it is not needed now.
                indexMap.put(itemDefinition, i);
            }
            i++;
        }

        itemDefinitionsList.sort(new Comparator<Object>() {
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
                // this definition does NOT have a target object already created
                // so the target object needs to be created, using the factory
                Object item = itemDefinition.getItemFactory().apply(itemDefinition.getData());

                // add the item to the definition
                itemDefinition.setItem(item);

                // add the item to the target list
                itemDefinitionsList.add(j, itemDefinition);
            }
            j++;
        }

        // todo run update function on all mapped items...

        // update the current state map
        // and clear the next state so it is ready for the next sync
        currentState = new LinkedHashMap<>(actualNextState);
        nextState.clear();
    }

    public static class ItemDefinition<I, O, M> {
        private String key;  // the key for this item
        private Object data;  // the definition data that will be used to create the item
        private Function<I, O> itemFactory;  // the function to use when creating the item
        private BiConsumer<I, O> update;  // the function used to update the item
        private Function<O, M> itemMapper;  // the function used to map the item to its final form for the target item
        private Object item;  // the item that is created from this definition
        private Object mappedItem;  // the item that is mapped from the item created from this definition

        public ItemDefinition() {
        }

        public ItemDefinition(String key, I data, Function<I, O> itemFactory, BiConsumer<I, O> update, Function<O, M> itemMapper) {
            this.key = key;
            this.data = data;
            this.itemFactory = itemFactory;
            this.update = update;
            this.itemMapper = itemMapper;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Function<I, O> getItemFactory() {
            return itemFactory;
        }

        public void setItemFactory(Function<I, O> factory) {
            this.itemFactory = factory;
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

        public Object getMappedItem() {
            return mappedItem;
        }

        public void setMappedItem(Object mappedItem) {
            this.mappedItem = mappedItem;
        }

        public Function<O, M> getItemMapper() {
            return itemMapper;
        }

        public void setItemMapper(Function<O, M> itemMapper) {
            this.itemMapper = itemMapper;
        }
    }
}