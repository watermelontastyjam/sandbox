package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositeFunctionTest {

    @Test
    void apply() {
        IdentityFunction iF = new IdentityFunction();
        SqrFunction sf = new SqrFunction();
        CompositeFunction cf = new CompositeFunction(iF,sf);
        CompositeFunction cf2 =  new CompositeFunction(sf,cf);
        MathFunction test2 = new AcosPlusAsin();
        MathFunction test = new CosDoubleArg();
        MathFunction CosTwoA = test.andThen(test2);
        MathFunction CosInSq = sf.andThen(test);
        double[] xValues = {1,3,5};
        double[] yValues = {2,4,6};
        MathFunction testar = new ArrayTabulatedFunction(xValues, yValues);
        MathFunction testl = new LinkedListTabulatedFunction(testar, 1,5, 3);

CompositeFunction tlar = new CompositeFunction( testl, sf);
        CompositeFunction tlar1 = new CompositeFunction( testl, iF);
        double expected = 100;
        double actual = cf.apply(10);
        assertEquals(expected,actual);
        expected = 10000;
        actual = cf2.apply(10);
        assertEquals(expected,actual);
        expected = 1.57;
        actual = Math.round(CosTwoA.apply(0)*100.0)/100.0;
        assertEquals(expected,actual);
        expected = 1;
        actual = CosInSq.apply(0);
        assertEquals(expected,actual);
         expected = 4;
         actual = tlar.apply(1);
        assertEquals(expected,actual);
        expected = 6;
        actual=tlar1.apply(5);
        assertEquals(expected,actual);

    }
}