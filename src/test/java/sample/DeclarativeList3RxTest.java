package sample;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Alessandro Scarlatti
 * @since Saturday, 5/2/2020
 */
public class DeclarativeList3RxTest {

    public static abstract class RxView<Props> {

        protected Props props;

        public void rxUpdateComponent(Props nextProps) {
            // update the props
            // but grab the current props so we can use it for the lifecycle method afterward
            Props prevProps = props;
            props = nextProps;

            // call the lifecycle method
            componentDidUpdate(prevProps);
        }

        protected void componentDidUpdate(Props prevProps) {
        }

        protected abstract Object render();
    }

    public static class RxViewItemDefinition<I, O extends RxView<I>, M> extends DeclarativeList3.ItemDefinition<I, O, M> {
        public RxViewItemDefinition(String key, I props, Function<I, O> itemFactory) {
            setKey(key);
            setData(props);
            setItemFactory(itemFactory);
            setUpdate((I nextProps, O view) -> {
                view.rxUpdateComponent(nextProps);
            });
            setItemMapper((O view) -> (M) view.render());
        }

        public RxViewItemDefinition(String key, I data, Function<I, O> itemFactory, BiConsumer<I, O> update, Function<O, M> itemMapper) {
            super(key, data, itemFactory, update, itemMapper);
        }
    }

    public static class Todo extends RxView<Todo.Props> {

        String text;

        Todo(Todo.Props props) {
            this.props = props;
        }

        public static RxViewItemDefinition<Todo.Props, Todo, String> todo(String key, Todo.Props props) {
            return new RxViewItemDefinition<>(key, props, p -> new Todo(p));
        }

        @Override
        protected void componentDidUpdate(Todo.Props prevProps) {

        }

        @Override
        protected Object render() {
            if (props.text.contains("test")) {
                return "item test";
            } else {
                return "item " + props.text;
            }
        }

        @Override
        public String toString() {
            return "Todo{" +
                "text='" + text + '\'' +
                '}';
        }

        static class Props {
            String text;

            public Props() {
            }

            public Props(String text) {
                this.text = text;
            }
        }
    }

    @Test
    public void testDeclarativeList() {

        List targetList = new ArrayList<>();
        DeclarativeList3 declarativeList = new DeclarativeList3(targetList);


        declarativeList.beginSync();
        declarativeList.put(Todo.todo("key1", new Todo.Props("val1")));
        declarativeList.put(Todo.todo("key1", new Todo.Props("val1")));
        declarativeList.put(Todo.todo("key2", new Todo.Props("val2")));
        declarativeList.put(Todo.todo("key3", new Todo.Props("val3")));
        declarativeList.put(Todo.todo("key4", new Todo.Props("val4")));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val1", "item val2", "item val3", "item val4"), targetList);

        declarativeList.beginSync();
        declarativeList.put(Todo.todo("key2", new Todo.Props("val2")));
        declarativeList.put(Todo.todo("key1", new Todo.Props("val1")));
        declarativeList.put(Todo.todo("key5", new Todo.Props("val5")));
        declarativeList.put(Todo.todo("key4", new Todo.Props("val4")));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val2", "item val1", "item val5", "item val4"), targetList);

        declarativeList.beginSync();
        declarativeList.put(Todo.todo("key6", new Todo.Props("val6")));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val6"), targetList);

        declarativeList.beginSync();
        declarativeList.put(Todo.todo("key6", new Todo.Props("val6.2")));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val6.2"), targetList);

        declarativeList.beginSync();
        declarativeList.put(Todo.todo("key1", new Todo.Props("val1")));
        declarativeList.put(Todo.todo("key6", new Todo.Props("val6.2")));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val1", "item val6.2"), targetList);

        declarativeList.beginSync();
        declarativeList.put(Todo.todo("key6", new Todo.Props("val6.3")));
        declarativeList.put(Todo.todo("key1", new Todo.Props("val1.1")));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val6.3", "item val1.1"), targetList);

        // update props without changing order
        declarativeList.beginSync();
        declarativeList.put(Todo.todo("key6", new Todo.Props("val6.4")));
        declarativeList.put(Todo.todo("key1", new Todo.Props("val.test")));
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
