package sample;

import org.junit.Assert;
import org.junit.Test;
import sample.DeclarativeList2.ItemDefinition;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Alessandro Scarlatti
 * @since Saturday, 5/2/2020
 */
public class DeclarativeList2Test {

    public static class Todo {
        public String text;

        public Todo(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return "item " + text;
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
        DeclarativeList2 declarativeList = new DeclarativeList2(targetList);

        Function<Todo, String> target = (todo -> todo.toString());

        Function<String, Object> todoItem = (String text) -> new Todo(text);
        BiConsumer<String, Todo> updateTodo = (String text, Todo todo) -> todo.text = text;

        declarativeList.beginSync();
        declarativeList.put(new ItemDefinition("key1", "val1", todoItem, updateTodo, target));
        declarativeList.put(new ItemDefinition("key2", "val2", todoItem, updateTodo, target));
        declarativeList.put(new ItemDefinition("key3", "val3", todoItem, updateTodo, target));
        declarativeList.put(new ItemDefinition("key4", "val4", todoItem, updateTodo, target));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val1", "item val2", "item val3", "item val4"), todoListToString(targetList));

        declarativeList.beginSync();
        declarativeList.put(new ItemDefinition("key2", "val2", todoItem, updateTodo, target));
        declarativeList.put(new ItemDefinition("key1", "val1", todoItem, updateTodo, target));
        declarativeList.put(new ItemDefinition("key5", "val5", todoItem, updateTodo, target));
        declarativeList.put(new ItemDefinition("key4", "val4", todoItem, updateTodo, target));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val2", "item val1", "item val5", "item val4"), todoListToString(targetList));

        declarativeList.beginSync();
        declarativeList.put(new ItemDefinition("key6", "val6", todoItem, updateTodo, target));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val6"), todoListToString(targetList));

        declarativeList.beginSync();
        declarativeList.put(new ItemDefinition("key6", "val6.2", todoItem, updateTodo, target));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val6.2"), todoListToString(targetList));

        declarativeList.beginSync();
        declarativeList.put(new ItemDefinition("key1", "val1", todoItem, updateTodo, target));
        declarativeList.put(new ItemDefinition("key6", "val6.2", todoItem, updateTodo, target));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val1", "item val6.2"), todoListToString(targetList));

        declarativeList.beginSync();
        declarativeList.put(new ItemDefinition("key6", "val6.3", todoItem, updateTodo, target));
        declarativeList.put(new ItemDefinition("key1", "val1.1", todoItem, updateTodo, target));
        declarativeList.endSync();

        Assert.assertEquals(Arrays.asList("item val6.3", "item val1.1"), todoListToString(targetList));
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
