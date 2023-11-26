package functions;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import exceptions.InterpolationException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTabulatedFunctionTest  {

    double[] xValues = {1,5,7,9,13,20};
    double[] yValues = {10,50,70,90,130,200};

    SqrFunction sf = new SqrFunction();

    LinkedListTabulatedFunction testListArray = new LinkedListTabulatedFunction(xValues,yValues);
    LinkedListTabulatedFunction testListArray2 = new LinkedListTabulatedFunction(xValues,yValues);
    LinkedListTabulatedFunction testListFunc= new LinkedListTabulatedFunction(sf,1,30,13);
    @Test
    void floorIndexOfX() {
        assertEquals(0, testListArray .floorIndexOfX(1));
        assertEquals(0, testListArray .floorIndexOfX(4));
        assertEquals(1, testListArray .floorIndexOfX(5.6));
        assertEquals(1, testListFunc.floorIndexOfX(6));
    }

    @Test
    void extrapolateLeft() {
        assertEquals(0,testListArray .extrapolateLeft(0));
        assertEquals(30,testListArray .extrapolateLeft(3));
        assertEquals(-10,testListArray .extrapolateLeft(-1));
        assertEquals(6.958333333333332,testListFunc.extrapolateLeft(2.3));

    }

    @Test
    void extrapolateRight() {
        assertEquals(0, testListArray .extrapolateLeft(0));
        assertEquals(-10.0, testListArray .extrapolateLeft(-1));
        assertEquals(11.083333333333334, testListFunc.extrapolateLeft(3.2));
    }

    @Test
    void interpolate() {
        assertEquals(22.5, testListArray .interpolate(2.25, testListArray .floorIndexOfX(3)));
        assertEquals(12, testListArray .interpolate(1.2, testListFunc.floorIndexOfX(1.3)));
        //assertEquals(-21.141666666666673, testListFunc.interpolate(2.2, testListArray .floorIndexOfX(9)));
        assertEquals(32.50277777777777, testListFunc.interpolate(5.6, testListFunc.floorIndexOfX(3.59)));
    }

    @Test
    void getCount() {
        assertEquals(6, testListArray .getCount());
        assertEquals(26, testListFunc.getCount());
    }

    @Test
    void getX() throws IllegalAccessException{
        assertEquals(1, testListArray .getX(0));
        assertEquals(8.75, testListFunc.getX(3));
    }

    @Test
    void getY() throws IllegalAccessException {
        assertEquals(130.0, testListArray .getY(4));
        assertEquals(1, testListFunc.getY(13));
    }

    @Test
    void setY() throws IllegalAccessException {
        testListArray .setY(0, -12);
        assertEquals(-12., testListArray .getY(0));
        testListFunc.setY(10, 100.890);
        assertEquals(100.890, testListFunc.getY(10));
    }

    @Test
    void indexOfX() {
        assertEquals(5,testListArray .indexOfX(20));
        assertEquals(-1,testListFunc.indexOfX(13));
    }

    @Test
    void indexOfY() {
        assertEquals(5,testListArray .indexOfY(200));
        assertEquals(-1,testListFunc.indexOfY(13));
    }

    @Test
    void leftBound() {
        assertEquals(1., testListArray .leftBound());
        assertEquals(1., testListFunc.leftBound());
    }

    @Test
    void rightBound() {
        assertEquals(20., testListArray .rightBound());
        assertEquals(31.999999999999993, testListFunc.rightBound());
    }
    @Test
    void floorNodeOfX()
    {
        assertEquals(0, testListArray .floorNodeOfX(1));
        assertEquals(2, testListArray .floorNodeOfX(9));
        assertEquals(1, testListArray .floorNodeOfX(5.6));
        assertEquals(1, testListFunc.floorNodeOfX(6));
    }
    @Test
    void remove()
    {
        testListArray .remove(1);
        assertEquals(-1,testListArray .indexOfX(5));
        testListArray .remove(4);
        assertEquals(-1,testListArray .indexOfX(20));
        testListArray .remove(0);
        assertEquals(-1,testListArray .indexOfX(1));
    }

    @Test
    void insert()
    {
        testListArray .insert(4, 40);
        assertEquals(1, testListArray .indexOfX(4));
        testListArray .insert(-1, -100);
        testListArray .insert(21, 3000);
        assertEquals(8, testListArray .indexOfX(21));
    }
    @Test
    void toStringListTest(){
        String t = testListArray.toString();
        assertEquals(t, testListArray.toString());
        t = testListFunc.toString();
        assertEquals(t, testListFunc.toString());
    }
    @Test
    void toStringNodeTest(){
        String t = testListArray.getNode(0).toString();
        assertEquals(t, testListArray.getNode(0).toString());
        t = testListFunc.getNode(0).toString();
        assertEquals(t, testListFunc.getNode(0).toString());
    }

    @Test
    void equalsLists(){

        assertEquals(false,testListArray.equals(testListFunc));
        assertEquals(true,testListArray.equals(testListArray2));

    }

    @Test
    void equalsNodes(){
        assertEquals(false,testListArray.getNode(0).equals(testListArray.getNode(4)));
        assertEquals(true,testListArray.getNode(0).equals(testListArray.getNode(0)));
        assertEquals(false,testListArray.getNode(0).equals(testListFunc.getNode(0)));


    }
    @Test
    void nodeClone() throws CloneNotSupportedException {

        LinkedListTabulatedFunction.Node n = (testListArray.getNode(0)).clone();
        assertEquals(true, n.equals(testListArray.getNode(0)));
        testListArray2 = new LinkedListTabulatedFunction(xValues,yValues);
        LinkedListTabulatedFunction.Node n1 = (testListArray.getNode(0)).clone();
        assertEquals(true, n1.equals(testListArray.getNode(0)));


    }
    @Test
    void listClone() throws CloneNotSupportedException{
        LinkedListTabulatedFunction clone = testListArray.clone();
        LinkedListTabulatedFunction clone2nd = clone.clone();
        assertEquals(true,testListArray.equals(clone));
        assertEquals(true,clone.equals(testListArray));
        assertEquals(true,testListArray.equals(clone2nd));

    }
    @Test
    void hashCodeTest(){
        assertEquals(-1945403471,testListArray.hashCode());
        assertEquals(-1945403471,testListArray2.hashCode());
        assertEquals(-716218838,testListFunc.hashCode());

    }
    @Test
    void getNodeException() throws IllegalArgumentException
    {
        try{
            testListArray.getNode(100);
            testListArray.getNode(-100);
            fail("Expected IllegalArgumentException");

        }catch(IllegalArgumentException exception)
        {
            assertNotEquals("", exception.getMessage());
        }

    }
    @Test
    void testConstructor() throws IllegalArgumentException{
        try{
            LinkedListTabulatedFunction testList = new LinkedListTabulatedFunction(new double[]{1},new double[]{2});
            fail("Expected IllegalArgumentException");
        }catch (IllegalArgumentException exception){
            assertNotEquals("", exception.getMessage());
        }
        try{
            LinkedListTabulatedFunction test = new LinkedListTabulatedFunction(new double[]{0,1},new double[]{1,0,0});
            fail("Expected DifferentLengthOfArraysException!");
        }
        catch(DifferentLengthOfArraysException exception){
            assertEquals("arrays of different length", exception.getMessage());
        }
        try{
            LinkedListTabulatedFunction test = new LinkedListTabulatedFunction(new double[]{2,1},new double[]{1,0});
            fail("Expected ArrayIsNotSortedException!");
        }
        catch(ArrayIsNotSortedException exception){
            assertEquals("array values aren't sorted in ascending order", exception.getMessage());
        }
    }
    @Test

    public void testInterpolationException() throws InterpolationException{
        assertThrows(InterpolationException.class, ()->{testListArray.interpolate(13.2,3);});
    }
    @Test
    void testFloorNodeNIndexOfX() throws IllegalArgumentException {
        try{
            testListArray.floorIndexOfX(-100);
            fail("Expected IllegalArgumentException");
        }catch (IllegalArgumentException exception)
        {
            assertNotEquals("",exception.getMessage());
        }
    }
    @Test
    void testRemoveException() throws IllegalArgumentException{
        try{
            testListArray.remove(-100);
            fail("Expected IllegalArgumentException");
        }catch (IllegalArgumentException exception)
        {
            assertNotEquals("",exception.getMessage());
        }
    }
    @Test
    void testIterator() throws NoSuchElementException{
        Iterator<Point> iterator = testListFunc.iterator();
        LinkedListTabulatedFunction.Node node = testListFunc.getNode(0);
        while(iterator.hasNext()) {
            Point point = iterator.next();
            assertEquals(node.x, point.x);
            assertEquals(node.y,point.y);
            node = node.next;
        }
        try{
            Point point = iterator.next();
            fail("Expected NoSuchElementException!");
        }catch (NoSuchElementException exception) {
            assertNotEquals("",exception.getMessage());
        }

        node = testListFunc.getNode(0);
        for(Point point: testListFunc)
        {
            assertEquals(node.x, point.x);
            assertEquals(node.y,point.y);
            node = node.next;
        }
    }
}