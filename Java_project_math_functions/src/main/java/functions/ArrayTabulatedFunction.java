package functions;
import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import exceptions.InterpolationException;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Arrays;
import java.util.Random;
import java.util.Iterator;
import java.lang.*;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Insertable,  Cloneable, Serializable{
    private double[] xValues = null;
    private double[] yValues = null;
    private static final long serialVersionUID = 1483522376531849211L;
    protected int count;


    public ArrayTabulatedFunction(double[] xValues, double[] yValues) throws IllegalArgumentException, DifferentLengthOfArraysException, ArrayIsNotSortedException {
        int size = xValues.length;
        if (size < 2)
            throw new IllegalArgumentException("Size of array less than 2!");
        checkLengthIsTheSame(xValues, yValues);
        checkSorted(xValues);
        this.xValues = Arrays.copyOf(xValues, size);
        this.yValues = Arrays.copyOf(yValues, size);
        count = xValues.length;
    }

    ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) throws IllegalArgumentException {
        if (count <2)
            throw new IllegalArgumentException("Size of array less than 2!");
        if (xFrom > xTo) {
            double tmp;
            tmp = xTo;
            xTo = xFrom;
            xFrom = tmp;
        }
        xValues = new double[count];
        yValues = new double[count];
        this.count = count;

        double step = (xFrom + xTo) / (count - 1);
        double xCordinate = xFrom;

        for (int i = 0; i < count; i++) {
            xValues[i] = xCordinate;
            yValues[i] = source.apply(xCordinate);
            xCordinate += step;
        }
    }
    protected int floorIndexOfX(double x) throws IllegalArgumentException{
        int index = 0;
        if (x < leftBound())
            throw new IllegalArgumentException("Arg less than left bound of the array!");
        while (index < count && xValues[index] < x) ++index;
        return (index == count || index == 0) ? index : --index;
    }
    protected double extrapolateLeft(double x) {
        //return interpolate(x, 0);
        return interpolate(x, getX(0), getX(1), getY(0), getY(1));
    }

    protected double extrapolateRight(double x) {
        //return interpolate(x, count - 2);
        return interpolate(x, getX(count-2), getX(count-1), getY(count-2), getY(count-1));
    }

    protected double interpolate(double x, int floorIndex) throws InterpolationException {
        if(x>this.getX(floorIndex+1)||x<this.getX(floorIndex)){ throw new InterpolationException("index in uninterpolated period");}
            return interpolate(x, getX(floorIndex), getX(floorIndex + 1), getY(floorIndex), getY(floorIndex + 1));
    }

    public int getCount() {
        return count;
    }

    public double getX(int index) {
        return xValues[index];
    }

    public double getY(int index) {
        return yValues[index];
    }

    public void setY(int index, double value) {
        yValues[index] = value;
    }

    public int indexOfX(double x) {
        return binarySearch(xValues, x);
    }

    public int indexOfY(double y) {
        return binarySearch(yValues, y);
    }

    private int binarySearch(double[] arr, double target) {
        int left = 0;
        int right = count - 1;
        double epsilon = 0.000_000_001;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (Math.abs(arr[mid] - target) < epsilon)
                return mid;
            else if (arr[mid] < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }

    public double leftBound() {
        return xValues[0];
    }

    public double rightBound() {
        return xValues[count - 1];
    }
    public void insert(double x, double y)
    {
        double[] newXValues = new double[count+1],newYValues = new double[count+1];

        int index = indexOfX(x);
        if(index != -1)
            yValues[index] = y;
        else{
            index = floorIndexOfX(x);
            if(index != count && index !=0) {
                System.arraycopy(xValues, 0, newXValues, 0, index+1);
                newXValues[index + 1] = x;
                System.arraycopy(xValues, index+1, newXValues, index + 2, count - index-1 );

                System.arraycopy(yValues, 0, newYValues, 0, index+1);
                newYValues[index + 1] = y;
                System.arraycopy(yValues, index+1, newYValues, index + 2, count - index-1 );
            }else if(index == count) {
                System.arraycopy(xValues, 0, newXValues, 0, count);
                newXValues[index] = x;

                System.arraycopy(yValues, 0, newYValues, 0, count);
                newYValues[index] = y;
            }
            else
            {
                System.arraycopy(xValues, 0, newXValues, 1, count);
                newXValues[index] = x;

                System.arraycopy(yValues, 0, newYValues, 1, count);
                newYValues[index] = y;
            }
            count++;
            xValues = Arrays.copyOf(newXValues, count);
            yValues = Arrays.copyOf(newYValues, count);

        }
    }
    public void remove(int index) throws IllegalArgumentException
    {
        if(index > count || index < 0)
            throw new IllegalArgumentException("Element with index = " + index + " doesn't exist");

        double[] newXValues = new double[count-1],newYValues = new double[count-1];
        if(index != count && index !=0) {
            System.arraycopy(xValues, 0, newXValues, 0, index-1);
            System.arraycopy(xValues, index+1, newXValues, index, count - index-1 );

            System.arraycopy(yValues, 0, newYValues, 0, index-1);
            System.arraycopy(yValues, index+1, newYValues, index, count - index-1 );
        }else if(index == count) {
            System.arraycopy(xValues, 0, newXValues, 0, count-2);

            System.arraycopy(yValues, 0, newYValues, 0, count-2);
        }
        else
        {
            System.arraycopy(xValues, 1, newXValues, 0, count-1);

            System.arraycopy(yValues, 1, newYValues, 0, count-1);
        }
        count--;
        xValues = Arrays.copyOf(newXValues, count);
        yValues = Arrays.copyOf(newYValues, count);

    }

    public double apply(double x) {
        if (x < leftBound())
            return extrapolateLeft(x);
        else if (x > rightBound())
            return extrapolateRight(x);
        else {
            int searchIndexOfX = indexOfX(x);

            if (searchIndexOfX != -1)
                return getY(searchIndexOfX);
            else
                return interpolate(x, floorIndexOfX(x));
        }
    }

//    @Override
//
//    public String toString(){
//        String inside_array = "";
//
//        for( int i =0; i<count;++i) inside_array+= '|' + " x = " + String.valueOf(getX(i)) + " y = " + String.valueOf(getY(i)) + " |\n";
//
//
//        return inside_array;
//    }

   @Override

    public boolean equals(Object o)
    {

        boolean comparison = true;
        if(this==o) return true;
        if(o.getClass()!=getClass()) return false;
        else{
            if(this.count!=((ArrayTabulatedFunction)o).count) return false;
            for(int i=0; i<this.count;++i) {
                if(this.getX(i)!=(((ArrayTabulatedFunction)o).getX(i))|| (this.getY(i)!=(((ArrayTabulatedFunction)o).getY(i))))
                    return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int res = 0;
        int MAX = 2^31-1;
        if(this.xValues!=null&&this.yValues!=null)
        {

            for(double tmpY:yValues) res +=MAX^(int)tmpY;
        }
        else {
            Random R1 = new Random(2^31-1);
            res= R1.nextInt();
        }

        return res;
    }

    @Override

    public Object clone() throws CloneNotSupportedException
    {
        ArrayTabulatedFunction array = (ArrayTabulatedFunction)  super.clone();
array.xValues = new double[xValues.length];
array.yValues = new double[yValues.length];
for(int i=0;i<xValues.length;++i) { array.xValues[i] = xValues[i];}
        for(int i=0;i<yValues.length;++i) { array.yValues[i] = yValues[i];}
        return array;

    }
    public Iterator iterator() {

        Iterator iterator = new Iterator() {

            int i;

            @Override
            public boolean hasNext() {
                return i < count;
            }

            @Override
            public Point next() {

                if (!hasNext()) throw new NoSuchElementException();

                Point newPoint = new Point(getX(i), getY(i));
                ++i;
                return newPoint;

            }

        };

        return iterator;

    }


}
