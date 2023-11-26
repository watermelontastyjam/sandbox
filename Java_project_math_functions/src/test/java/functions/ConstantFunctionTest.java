package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantFunctionTest {

    @Test
    void apply() {
        ConstantFunction c = new ConstantFunction(3);
        assertAll(
                ()-> assertEquals(3, c.apply(3)),
                ()-> assertEquals(3, c.apply(-3)),
                ()-> assertEquals(3, c.apply(2)));
    }
}