package sample;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Alessandro Scarlatti
 * @since Saturday, 5/2/2020
 */
public class DeclarativeList3RxTest {

    public static abstract class RxView {

        protected Map<String, Object> props;

        public void rxUpdateComponent(Map<String, Object> nextProps) {
            // update the props
            // but grab the current props so we can use it for the lifecycle method afterward
            Map<String, Object> prevProps = props;
            props = nextProps;

            // call the lifecycle method
            componentDidUpdate(prevProps);
        }

        protected void componentDidUpdate(Map<String, Object> prevProps) {
        }

        protected abstract Object render();
    }

    public static class RxViewElementDefinition<O extends RxView, M> extends DeclarativeList3.ElementDefinition<Map<String, Object>, O, M> {
        public RxViewElementDefinition(String key, Map<String, Object> props, Function<Map<String, Object>, O> itemFactory) {
            setKey(key);
            setData(props);
            setItemFactory(itemFactory);
            setUpdate((Map<String, Object> nextProps, O view) -> {
                view.rxUpdateComponent(nextProps);
            });
            setItemMapper((O view) -> (M) view.render());
        }

        public RxViewElementDefinition(String key, Map<String, Object> data, Function<Map<String, Object>, O> itemFactory, BiConsumer<Map<String, Object>, O> update, Function<O, M> itemMapper) {
            super(key, data, itemFactory, update, itemMapper);
        }
    }

    public static class Todo extends RxView {

        String text;

        Todo(Map<String, Object> props) {
            try {
                this.props = props;
                this.text = (String) props.get("text");
//                Todo.Props thisProps = new Todo.Props();
//                BeanUtils.populate(thisProps, props);
            } catch (Exception e) {
                throw new RuntimeException("Error populating props from map " + props, e);
            }
        }

        @Override
        protected void componentDidUpdate(Map<String, Object> prevProps) {
            text = (String) props.get("text");
        }

        @Override
        protected Object render() {
            if (text.contains("test")) {
                return "item test";
            } else {
                return "item " + text;
            }
        }

        @Override
        public String toString() {
            return "Todo{" +
                "text='" + text + '\'' +
                '}';
        }

//        public static class Props {
//            private String text;
//
//            public String getText() {
//                return text;
//            }
//
//            public void setText(String text) {
//                this.text = text;
//            }
//        }
    }

    private static class Prop {
        String key;
        Object value;

        public Prop(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    private static DeclarativeList3.ElementDefinition element(Class<?> clazz, Prop... prop) {
        return new DeclarativeList3.ElementDefinition();
    }

    private static DeclarativeList3.ElementDefinition element(Class<?> clazz, Object... prop) {
        return new DeclarativeList3.ElementDefinition();
    }

    private static DeclarativeList3.ElementDefinition element(Class<?> clazz, Map<String, Object> props) {
        // todo would use reflection to find the correct constructor...
        return new RxViewElementDefinition((String) props.get("key"), props, p -> new Todo((Map)p));
    }

    private static Map<String, Object> props(Object...args) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            String key = (String) args[i];
            Object value = args[i + 1];
            map.put(key, value);
        }

        return map;
    }

    private static Prop prop(String key, Object value) {
        return new Prop(key, value);
    }

    @Test
    public void testDeclarativeList() {

        List targetList = new ArrayList<>();
        DeclarativeList3 declarativeList = new DeclarativeList3(targetList);

//        declarativeList.put(element(Todo.class, props(
//            "key", "key1",
//            "color", "color red"
//        )));
//
//        declarativeList.put(element(Todo.class, props("key", "key1", "color", "color red")));
//
//        declarativeList.put(element(Todo.class,
//            prop("text", "val1"),
//            prop("color", "val1"),
//            prop("onClick", "val1")));
//
//        declarativeList.put(element(Todo.class,
//            "text", "val1",
//            "color", "val1",
//            "onClick", "val1"));

        declarativeList.beginSync();
        declarativeList.put(element(Todo.class, props("key", "key1", "text", "val1")));
        declarativeList.put(element(Todo.class, props("key", "key1", "text", "val1")));
        declarativeList.put(element(Todo.class, props("key", "key2", "text", "val2")));
        declarativeList.put(element(Todo.class, props("key", "key3", "text", "val3")));
        declarativeList.put(element(Todo.class, props("key", "key4", "text", "val4")));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val1", "item val2", "item val3", "item val4"), targetList);

        declarativeList.beginSync();
        declarativeList.put(element(Todo.class, props("key", "key2", "text", "val2")));
        declarativeList.put(element(Todo.class, props("key", "key1", "text", "val1")));
        declarativeList.put(element(Todo.class, props("key", "key5", "text", "val5")));
        declarativeList.put(element(Todo.class, props("key", "key4", "text", "val4")));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val2", "item val1", "item val5", "item val4"), targetList);

        declarativeList.beginSync();
        declarativeList.put(element(Todo.class, props("key", "key6", "text", "val6")));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val6"), targetList);

        declarativeList.beginSync();
        declarativeList.put(element(Todo.class, props("key", "key6", "text", "val6.2")));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val6.2"), targetList);

        declarativeList.beginSync();
        declarativeList.put(element(Todo.class, props("key", "key1", "text", "val1")));
        declarativeList.put(element(Todo.class, props("key", "key6", "text", "val6.2")));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val1", "item val6.2"), targetList);

        declarativeList.beginSync();
        declarativeList.put(element(Todo.class, props("key", "key6", "text", "val6.3")));
        declarativeList.put(element(Todo.class, props("key", "key1", "text", "val1.1")));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val6.3", "item val1.1"), targetList);

        // update props without changing order
        declarativeList.beginSync();
        declarativeList.put(element(Todo.class, props("key", "key6", "text", "val6.4")));
        declarativeList.put(element(Todo.class, props("key", "key1", "text", "val.test")));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val6.4", "item test"), targetList);

    }

    private List<String> todoListToString(List<Todo> todos) {
//        List<String> strings = new ArrayList<>();
//        for (Todo todo : todos) {
//            strings.add(todo.toString());
//        }
//
//        return strings;
        return (List) todos;
    }

    @Test
    public void testSyncSort() {
        List<String> targetList = new ArrayList<>(Arrays.asList("val2", "val3", "val1"));
        List<String> sourceList = new ArrayList<>(Arrays.asList("val1", "val2", "val3"));

        targetList.sort(Comparator.comparingInt(sourceList::indexOf));

        Assert.assertEquals(Arrays.asList("val1", "val2", "val3"), targetList);
    }
}
