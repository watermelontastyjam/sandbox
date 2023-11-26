package functions.factory;

import functions.ArrayTabulatedFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTabulatedFunctionFactoryTest {

    @Test
    void create() {
        double[] xValues = {1,2,3,4,5};
        double[] yValues = {9,18,27,36,45};
        ArrayTabulatedFunctionFactory arrayFunctionFactoryTest = new ArrayTabulatedFunctionFactory();
        assertEquals(ArrayTabulatedFunction.class,arrayFunctionFactoryTest.create(xValues,yValues).getClass());
    }
}