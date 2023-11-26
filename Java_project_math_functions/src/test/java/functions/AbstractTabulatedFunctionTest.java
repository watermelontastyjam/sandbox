package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractTabulatedFunctionTest {

    @Test
    void toStringTest(){

        double[] xValues = {1,5,7,9,13,20};
        double[] yValues = {10,50,70,90,130,200};

        SqrFunction sf = new SqrFunction();

        AbstractTabulatedFunction testListArray = new LinkedListTabulatedFunction(xValues,yValues);

        AbstractTabulatedFunction testListFunc= new LinkedListTabulatedFunction(sf,1,30,13);
        ArrayTabulatedFunction testArrayFunc = new ArrayTabulatedFunction(xValues, yValues);
        assertEquals("LinkedListTabulatedFunction size = 6\n[1.0;10.0]\n[5.0;50.0]\n[7.0;70.0]\n[9.0;90.0]\n[13.0;130.0]\n[20.0;200.0]\n",testListArray.toString());
        assertEquals("ArrayTabulatedFunction size = 6\n[1.0;10.0]\n[5.0;50.0]\n[7.0;70.0]\n[9.0;90.0]\n[13.0;130.0]\n[20.0;200.0]\n",testArrayFunc.toString());

    }
}