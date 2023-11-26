package functions;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class StrictTabulatedFunctionTest {


    double[] xValues = {1,5,7,9,13,20};
    double[] yValues = {10,50,70,90,130,200};

    SqrFunction sf = new SqrFunction();

    LinkedListTabulatedFunction testListArray = new LinkedListTabulatedFunction(xValues,yValues);
    ArrayTabulatedFunction ar = new ArrayTabulatedFunction(xValues, yValues);
    StrictTabulatedFunction strictTabulatedFunctionTestArray = new StrictTabulatedFunction(ar);
    StrictTabulatedFunction strictTabulatedFunctionTestList = new StrictTabulatedFunction(testListArray);



    @Test
    void apply() {
        assertEquals(10,strictTabulatedFunctionTestArray.apply(1));
        assertEquals(90,strictTabulatedFunctionTestList.apply(9));
        try{
            strictTabulatedFunctionTestArray.apply(100);
            fail("Expected UnsupportedOperationException!");
        }catch (UnsupportedOperationException exception){
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    void getCount() {
        assertEquals(6, strictTabulatedFunctionTestArray .getCount());
        assertEquals(6, strictTabulatedFunctionTestList.getCount());
    }

    @Test
    void getX() {
        assertEquals(1, strictTabulatedFunctionTestList .getX(0));
        assertEquals(9, strictTabulatedFunctionTestArray.getX(3));
    }

    @Test
    void getY() {
        assertEquals(130.0, strictTabulatedFunctionTestArray .getY(4));
        assertEquals(50, strictTabulatedFunctionTestList.getY(1));
    }

    @Test
    void setY() {
        strictTabulatedFunctionTestList .setY(0, -12);
        assertEquals(-12., strictTabulatedFunctionTestList .getY(0));
        strictTabulatedFunctionTestArray.setY(3, 100.890);
        assertEquals(100.890, strictTabulatedFunctionTestArray.getY(3));
    }

    @Test
    void indexOfX() {
        assertEquals(5,strictTabulatedFunctionTestArray .indexOfX(20));
        assertEquals(4,strictTabulatedFunctionTestList.indexOfX(13));
    }

    @Test
    void indexOfY() {
        assertEquals(5,strictTabulatedFunctionTestList.indexOfY(200));
        assertEquals(-1,strictTabulatedFunctionTestArray.indexOfY(13));
    }

    @Test
    void leftBound() {

        assertEquals(1., strictTabulatedFunctionTestArray.leftBound());
        assertEquals(1., strictTabulatedFunctionTestList.leftBound());
    }

    @Test
    void rightBound() {

        assertEquals(20, strictTabulatedFunctionTestArray.rightBound());
        assertEquals(20, strictTabulatedFunctionTestList.rightBound());
    }

}