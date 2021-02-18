import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BasketTest {

    private static final double PRICE_DELTA = 0.001;

    private Basket basket;
    private Item toy;
    private Item teddyBear;

    private static Map<Item, Integer> createOrder(Object ... mapContent) {
        Map<Item, Integer> result = new HashMap<>();

        for (int index = 0; index < mapContent.length; index += 2) {
            Item item = (Item) mapContent[index];
            Integer quantity = (Integer) mapContent[index + 1];
            result.put(item, quantity);
        }

        return result;
    }

    @Before
    public void setUp() {
        toy = new Item("toy", 39.99);
        teddyBear = new Item("teddy bear", 59.99);
        basket = new Basket();
    }

    @Test
    public void shouldAllowToAddItemToBasket() {
        basket.add(toy);
        Map<Item, Integer> expected = createOrder(toy, 1);
        assertEquals(expected, basket.getOrder());
    }

    @Test
    public void shouldAllowToAddSameItemToBasketTwice() {
        basket.add(toy);
        basket.add(toy);
        Map<Item, Integer> expected = createOrder(toy, 2);
        assertEquals(expected, basket.getOrder());
    }

    @Test
    public void shouldAllowToAddItemToBasketWithQuantityOne() {
        basket.add(toy,1);
        Map<Item, Integer> expected = createOrder(toy, 1);
        assertEquals(expected, basket.getOrder());
    }
    @Test
    public void shouldAllowToAddItemToBasketWithQuantityMany() {
        basket.add(toy,5);
        Map<Item, Integer> expected = createOrder(toy, 5);
        assertEquals(expected, basket.getOrder());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldntAllowToAddItemWithQuantityZero() {
        basket.add(toy,0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldntAllowToAddItemWithNegativeQuantity() {
        basket.add(toy,-10);
    }

    @Test
    public void shouldAllowToRemoveItemFromBasket() {
        basket.add(toy,2);
        basket.remove(toy);
        Map<Item, Integer> expected = createOrder(toy, 1);
        assertEquals(expected, basket.getOrder());
    }

    @Test
    public void shouldRemoveAllItemsFromBasket() {
        basket.add(toy);
        basket.remove(toy);
        Map<Item, Integer> expected = Collections.emptyMap();
        assertEquals(expected, basket.getOrder());
    }

    @Test
    public void shouldAllowToRemoveManyItemFromBasketAtOnce() {
        basket.add(toy,5);
        basket.remove(toy,3);
        Map<Item, Integer> expected = createOrder(toy, 2);
        assertEquals(expected, basket.getOrder());
    }

    @Test
    public void shouldAllowToRemoveManyDifferentItemFromBasketAtOnce() {
        basket.add(toy,5);
        basket.add(teddyBear,7);
        basket.remove(toy,3);
        basket.remove(teddyBear,5);
        Map<Item, Integer> expected = createOrder(toy, 2, teddyBear, 2);
        assertEquals(expected, basket.getOrder());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldTrowExcptionWhenRemoving0Items() {
        basket.remove(toy, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldTrowExcptionWhenRemovingNegativeQuantity() {
        basket.remove(toy, -50);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenThereIsNotThatManyItemsToRemove() {
        basket.add(toy,5);
        basket.remove(toy,7);
    }

    @Test
    public void shouldComputeSimpleOrderValue() {
        basket.add(toy,3);
        double expectedValue = toy.getPrice()*3;
        assertEquals(expectedValue, basket.getOrderValue(),PRICE_DELTA);
    }

    @Test
    public void shouldComputeOrderWithManyItemsValue() {
        basket.add(toy,7);
        basket.add(teddyBear,4);

        double expectedValue = toy.getPrice()*7 + teddyBear.getPrice()*4;
        assertEquals(expectedValue, basket.getOrderValue(), PRICE_DELTA);
    }

    @Test
    public void shouldPrintSimpleOrder() {
        basket.add(toy, 2);

        String expectedValue = String.format(Basket.ITEM_ORDER_FORMAT, toy.getName(), toy.getPrice(), 2, toy.getPrice() * 2) +
                System.lineSeparator() +
                String.format("Total: %.2f", 79.98);
        assertEquals(expectedValue, basket.toString());
    }

    @Test
    public void shouldDifferentiateBetweenToysWithSameNameDifferentPrice() {
        basket.add(new Item("otherToy", 10));
        basket.add(new Item("otherToy", 20));

        String expectedValue = String.format(Basket.ITEM_ORDER_FORMAT, "otherToy", 10.0, 1, 10.0) +
                System.lineSeparator() +
                String.format(Basket.ITEM_ORDER_FORMAT, "otherToy", 20.0, 1, 20.0) +
                System.lineSeparator() +
                String.format("Total: %.2f", 30.0);
        assertEquals(expectedValue, basket.toString());
    }
}