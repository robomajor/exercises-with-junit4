import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class RangeTest {
    private static Range range;

    @BeforeClass
    public static void setUpClass() {
        range = new Range(10, 20);
    }

    @Test
    public void shouldSayThat15rIsInRange() {
        assertTrue(range.isInRange(15));
    }

    @Test
    public void shouldSayThat25rIsNotInRange() {
        assertFalse(range.isInRange(25));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrownIllegalArgumentExceptionOnWrongParameters() {
        range = new Range(20, 10);
    }

    @Test
    public void shouldHaveProperErrorMessage() {
        try {
            range = new Range(20, 10);
            fail("Exception wasn't thrown!");
        } catch (IllegalArgumentException exception) {
            assertEquals("lowerBound is bigger than upperBound!", exception.getMessage());
        }
    }



}