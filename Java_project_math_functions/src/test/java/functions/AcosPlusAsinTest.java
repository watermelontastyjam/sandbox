package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AcosPlusAsinTest{
MathFunction d = new AcosPlusAsin();
@Test
public void testEquals()
{
    assertEquals(Math.PI/2, d.apply(0));
}
}