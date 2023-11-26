package functions;

import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.*;

public class MockTabulatedFunctionTest extends TestCase {
    MockTabulatedFunction obj = new MockTabulatedFunction();

    public void testInterpolate() {
        assertEquals(1., obj.interpolate(1, 0, 1, 0, 1));
    }
    public void testApply(){
        assertEquals(0.0, obj.apply(0));
        assertEquals(1.0, obj.apply(1));
        assertEquals(2.0, obj.apply(2));
        assertEquals(-1.0, obj.apply(-1));
    }
}
