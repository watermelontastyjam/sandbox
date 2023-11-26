package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZeroFunctionTest {

    @Test
    void apply() {
        ZeroFunction d = new ZeroFunction();
                assertEquals(0, d.apply(0));
    }
}