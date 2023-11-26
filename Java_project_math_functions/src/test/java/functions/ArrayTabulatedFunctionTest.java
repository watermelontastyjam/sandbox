package functions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import exceptions.InterpolationException;
import java.util.Iterator;


public class ArrayTabulatedFunctionTest {
    double[] xValue = {1, 3, 5, 7, 9};
    double[] yValue = {2, 4, 6, 8, 10};
    double[] xVal = {0,1,2,3};
    double[] yVal = {0,1,2,3};

    MathFunction sqrFunction = new SqrFunction();
    ArrayTabulatedFunction test_to_obj_methods = new ArrayTabulatedFunction(xVal, yVal);

    ArrayTabulatedFunction ar = new ArrayTabulatedFunction(xValue, yValue);
    ArrayTabulatedFunction sar =  new ArrayTabulatedFunction(sqrFunction, 0, 100, 10);
@Test
    public void testFloorIndexOfX() {
        assertEquals(0, ar.floorIndexOfX(2));
        assertEquals(1, ar.floorIndexOfX(4.9));
        assertEquals(1, ar.floorIndexOfX(5));
        assertEquals(2, ar.floorIndexOfX(6));
    }
@Test
    public void testExtrapolateLeft() {
        assertEquals(1.0, ar.extrapolateLeft(0));
        assertEquals(0.0, ar.extrapolateLeft(-1));
        assertEquals(-22.2, sar.extrapolateLeft(-2), 0.1);
    }
@Test
    public void testExtrapolateRight() {
        assertEquals(12., ar.extrapolateRight(11));
        assertEquals(10377.7, sar.extrapolateRight(102), 0.1);
    }
@Test
    public void testInterpolate() {
        assertEquals(3.25, ar.interpolate(2.25, ar.floorIndexOfX(2.25)));
        assertEquals(3., ar.interpolate(2., sar.floorIndexOfX(2.)));
    }
@Test
    public void testGetCount() {
        assertEquals(5, ar.getCount());
        assertEquals(10, sar.getCount());
    }
@Test
    public void testGetX() {
        assertEquals(1., ar.getX(0));
        assertEquals(5., ar.getX(2));
        assertEquals(0., sar.getX(0));
        assertEquals(33.3, sar.getX(3), 0.1);
    }
@Test
    public void testGetY() {
        assertEquals(2., ar.getY(0));
        assertEquals(6., ar.getY(2));
    }
@Test
    public void testSetY() {
        ar.setY(0, -12);
        assertEquals(-12., ar.getY(0));
    }
@Test
    public void testIndexOfX() {
        assertEquals(0,ar.indexOfX(1));
        assertEquals(-1,ar.indexOfX(10));
    }
@Test
    public void testIndexOfY() {
        assertEquals(1,ar.indexOfY(4.));
        assertEquals(-1,ar.indexOfY(3.1));
    }
@Test
    public void testLeftBound() {
        assertEquals(1., ar.leftBound());
        assertEquals(0., sar.leftBound());
    }
@Test
    public void testRightBound() {
        assertEquals(9., ar.rightBound());
        assertEquals(100.00000000000001, sar.rightBound());
    }
@Test
    public void testInsert(){
        ar.insert(11,12);
        assertEquals(5, ar.indexOfX(11) );
        ar.insert(6,7);
        assertEquals(6, ar.indexOfX(11) );
        ar.insert(5.9,0.2);
        assertEquals(-1, ar.indexOfX(0.1) );

    }
@Test
    public void testRemove()
    {
        ar.remove(2);
        assertEquals(7., ar.getX(2));
    }

@Test
    public void testToStringTest(){
     String inside_array = "| x = 0.0 y = 0.0 |\n| x = 1.0 y = 1.0 |\n| x = 2.0 y = 2.0 |\n| x = 3.0 y = 3.0 |\n";
     assertEquals(inside_array,test_to_obj_methods.toString() );
    }

    Object test_to_obj_methods_new = new ArrayTabulatedFunction(xVal, yVal);
@Test
    public void testEqualsTest()
    {
        assertFalse(test_to_obj_methods.equals(ar));
        assertTrue(test_to_obj_methods.equals(test_to_obj_methods));
        assertFalse(test_to_obj_methods.equals(sqrFunction));
        assertTrue(test_to_obj_methods.equals(test_to_obj_methods_new));
    }
@Test
    public void testHashCodeTest()
    {
        int square_array_hash = sar.hashCode();
        int test_to_obj_methods_hash = test_to_obj_methods.hashCode();
        int test_to_obj_methods_new_hash = test_to_obj_methods_new.hashCode();
        assertEquals(test_to_obj_methods_hash, test_to_obj_methods_new_hash);
        assertFalse(square_array_hash ==test_to_obj_methods_hash );
    }
    @Test
    public void testCloneTest() throws CloneNotSupportedException
    {
    Object copied_sar = sar.clone();
    assertEquals(sar, copied_sar);
    Object copied_test_to_obj_methods = test_to_obj_methods.clone();
    assertEquals(test_to_obj_methods, copied_test_to_obj_methods);
    }

    @Test
    public void testGetXException() throws IndexOutOfBoundsException {
        try{
            ar.getX(100);
            fail("Expected IndexOutOfBoundsException!");
        }catch (IndexOutOfBoundsException exception)
        {
            assertEquals("Index 100 out of bounds for length 5", exception.getMessage());
        }
    }
    @Test
    public void testConstructorException() throws IllegalArgumentException{
        try{
            ArrayTabulatedFunction test = new ArrayTabulatedFunction(new double[]{1},new double[]{0});
            fail("Expected IllegalArgumentException!");
        }catch (IllegalArgumentException exception)
        {
            assertEquals("Size of array less than 2!", exception.getMessage());
        }
        try{
            ArrayTabulatedFunction test = new ArrayTabulatedFunction(new double[]{0,1},new double[]{0,0,0});
            fail("Expected DifferentLengthOfArraysException!");
        }
        catch(DifferentLengthOfArraysException exception){
            assertEquals("arrays of different length", exception.getMessage());
        }
        try{
    ArrayTabulatedFunction test = new ArrayTabulatedFunction(new double[]{1,0},new double[]{1,0});
    fail("Expected ArrayIsNotSortedException!");
        }
        catch(ArrayIsNotSortedException exception){
assertEquals("array values aren't sorted in ascending order", exception.getMessage());
        }
    }

    @Test

    public void testInterpolationException() throws InterpolationException{
            assertThrows(InterpolationException.class, ()->{ar.interpolate(5,3);});
    }
    @Test
    public void testFloorIndexOfXException() throws IllegalArgumentException{
        try{
            ar.floorIndexOfX(-1);
            fail("Expected IllegalArgumentException!");
        }catch(IllegalArgumentException exception){
            assertEquals("Arg less than left bound of the array!", exception.getMessage());
        }
    }
    @Test
    public void testRemoveException() throws IllegalArgumentException{
        try{
            ar.remove(ar.getCount()+1);
            fail("Expected IllegalArgumentException!");
        }catch(IllegalArgumentException exception){
            assertEquals("Element with index = 6 doesn't exist",exception.getMessage());
        }
    }

    @Test

    public void testIteratorWithLoops() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] yValues = {10.0, 20.0, 30.0, 40.0, 50.0};
        ArrayTabulatedFunction tabulatedFunction = new ArrayTabulatedFunction(xValues, yValues);

        Iterator<Point> iterator = tabulatedFunction.iterator();

        int i = 0;
        while (iterator.hasNext()) {
            Point point = iterator.next();
            assertEquals(xValues[i], point.x, 0.0001);
            assertEquals(yValues[i], point.y, 0.0001);
            i++;
        }
        i=0;
        Iterator<Point> iter2 = tabulatedFunction.iterator();
        for (Point point : tabulatedFunction){
            point = iter2.next();
            assertEquals(point.x,xValues[i]);
            assertEquals(point.y,yValues[i]);
            i++;
        }
    }

}

