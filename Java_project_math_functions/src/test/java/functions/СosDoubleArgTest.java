package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CosDoubleArgTest {
MathFunction f = new CosDoubleArg();
@Test
    public void testEquals()
{
    assertEquals(-1, f.apply(Math.PI/2));
    assertEquals(1, f.apply(Math.PI*2));
}
}