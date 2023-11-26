package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefinedIntegralTest {

    MathFunction e = new CosDoubleArg();

    MathFunction s = new SqrFunction();

    MathFunction se = new CompositeFunction(e,s);
    DefinedIntegral d = new DefinedIntegral(e);

    DefinedIntegral fif = new DefinedIntegral(se);

    @Test
    public void apply()
    {
        assertEquals(0.455, d.apply(1));
        assertEquals(-0.378, d.apply(2));
        assertEquals(1.124, fif.apply(2));
    }


}