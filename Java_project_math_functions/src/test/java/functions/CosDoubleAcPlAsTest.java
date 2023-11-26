package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CosDoubleAcPlAsTest {
    MathFunction g = new CosDoubleAcPlAs();
@Test
    public void testEquals(){
    assertEquals(-1, g.apply(0));
    }

}