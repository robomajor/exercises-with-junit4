import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void twoItemsWIthTheSamePriceAndNameShouldBeEqual() {
        assertEquals(new Item("item",123.45), new Item("item", 123.45));
    }

    @Test
    public void twoItemsWIthDifferentNameShouldNotBeEqual() {
        assertNotEquals(new Item("item1",123.45), new Item("item2", 123.45));
    }

    @Test
    public void twoItemsWIthDifferentPricesShouldNotBeEqual() {
        assertNotEquals(new Item("item",123.45), new Item("item", 123.67));
    }

    @Test
    public void itemsWithTheSameNameShouldHaveTheSameHashCode() {
        assertEquals(new Item("item", 123).hashCode(), new Item("item", 321).hashCode());
    }

    @Test
    public void itemsWithDifferentNameShouldHaveDifferentHashCode() {
        assertNotEquals(new Item("item1", 123).hashCode(), new Item("item2", 321).hashCode());
    }

}