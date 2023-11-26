package functions;

import org.junit.jupiter.api.Test;

import java.util.logging.XMLFormatter;

import static org.junit.jupiter.api.Assertions.*;

class LimitFunctionTest {

    @Test
    void apply() {
        AcosPlusAsin f = new AcosPlusAsin();
        LimitFunction lim = new LimitFunction(f);
        assertEquals(1.5707963267948966,lim.apply(0.5));
        assertEquals(1.5707963267948966,lim.apply(-0.5));
        assertEquals(1.5707963267948968,lim.apply(1));
        assertEquals(1.5707963267948966,lim.apply(0));

        CosDoubleAcPlAs f2 = new CosDoubleAcPlAs();
        lim = new LimitFunction(f2);
        assertEquals(-1,lim.apply(0.5));
        assertEquals(-1,lim.apply(-0.5));
        assertEquals(-1,lim.apply(1));
        assertEquals(-1,lim.apply(0));

        CosDoubleArg f3 = new CosDoubleArg();
        lim = new LimitFunction(f3);
        assertEquals(-1,lim.apply(Math.PI/2));
        assertEquals(-1,lim.apply(-Math.PI/2));
        assertEquals(1,lim.apply(Math.PI));
        assertEquals(1,lim.apply(0));

    }
}