package sample;

import org.junit.Assert;
import org.junit.Test;
import sample.DeclarativeList3.ElementDefinition;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Alessandro Scarlatti
 * @since Saturday, 5/2/2020
 */
public class DeclarativeList3Test {

    public static class Todo {
        public String text;

        public Todo(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return "Todo{" +
                "text='" + text + '\'' +
                '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Todo todo = (Todo) o;
            return Objects.equals(text, todo.text);
        }

        @Override
        public int hashCode() {

            return Objects.hash(text);
        }
    }

    @Test
    public void testDeclarativeList() {

        List targetList = new ArrayList<>();
        DeclarativeList3 declarativeList = new DeclarativeList3(targetList);

        Function<String, Todo> todoFactory = (String text) -> new Todo(text);
        BiConsumer<String, Todo> updateTodo = (String text, Todo todo) -> todo.text = text;
        Function<Todo, String> mapper = (todo -> {
            if (todo.text.contains("test")) {
                return "item test";
            } else {
                return "item " + todo.text;
            }
        });

        declarativeList.beginSync();
        declarativeList.put(new DeclarativeList3.ElementDefinition("key1", "val1", todoFactory, updateTodo, mapper));
        declarativeList.put(new DeclarativeList3.ElementDefinition("key2", "val2", todoFactory, updateTodo, mapper));
        declarativeList.put(new ElementDefinition("key3", "val3", todoFactory, updateTodo, mapper));
        declarativeList.put(new DeclarativeList3.ElementDefinition("key4", "val4", todoFactory, updateTodo, mapper));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val1", "item val2", "item val3", "item val4"), targetList);

        declarativeList.beginSync();
        declarativeList.put(new ElementDefinition("key2", "val2", todoFactory, updateTodo, mapper));
        declarativeList.put(new DeclarativeList3.ElementDefinition("key1", "val1", todoFactory, updateTodo, mapper));
        declarativeList.put(new DeclarativeList3.ElementDefinition("key5", "val5", todoFactory, updateTodo, mapper));
        declarativeList.put(new ElementDefinition("key4", "val4", todoFactory, updateTodo, mapper));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val2", "item val1", "item val5", "item val4"), targetList);

        declarativeList.beginSync();
        declarativeList.put(new DeclarativeList3.ElementDefinition("key6", "val6", todoFactory, updateTodo, mapper));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val6"), targetList);

        declarativeList.beginSync();
        declarativeList.put(new ElementDefinition("key6", "val6.2", todoFactory, updateTodo, mapper));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val6.2"), targetList);

        declarativeList.beginSync();
        declarativeList.put(new ElementDefinition("key1", "val1", todoFactory, updateTodo, mapper));
        declarativeList.put(new DeclarativeList3.ElementDefinition("key6", "val6.2", todoFactory, updateTodo, mapper));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val1", "item val6.2"), targetList);

        declarativeList.beginSync();
        declarativeList.put(new ElementDefinition("key6", "val6.3", todoFactory, updateTodo, mapper));
        declarativeList.put(new DeclarativeList3.ElementDefinition("key1", "val1.1", todoFactory, updateTodo, mapper));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val6.3", "item val1.1"), targetList);

        // update props without changing order
        declarativeList.beginSync();
        declarativeList.put(new ElementDefinition("key6", "val6.4", todoFactory, updateTodo, mapper));
        declarativeList.put(new DeclarativeList3.ElementDefinition("key1", "val.test", todoFactory, updateTodo, mapper));
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
