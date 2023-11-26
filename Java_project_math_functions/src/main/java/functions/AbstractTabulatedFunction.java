package functions;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;

public abstract class AbstractTabulatedFunction implements TabulatedFunction{
    protected int count;
    abstract protected int floorIndexOfX(double x);
    abstract protected double extrapolateLeft(double x);
    abstract protected double extrapolateRight(double x);
    abstract protected double interpolate(double x, int floorIndex);
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (rightY - leftY) / (rightX - leftX) * (x - leftX);
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
     public static void checkLengthIsTheSame(double[] xValues, double[] yValues) {
            if(xValues.length != yValues.length) throw new DifferentLengthOfArraysException("arrays of different length");
     }
    public static void checkSorted(double[] xValues) {
        for (int i = 0; i < xValues.length - 1; ++i) {
            if (xValues[i] > xValues[i + 1])
                throw new ArrayIsNotSortedException("array values aren't sorted in ascending order");
        }
    }
    @Override
    public String toString(){
        StringBuilder word = new StringBuilder();
        word.append(this.getClass().getSimpleName()).append(" size = ").append(this.getCount()).append("\n");
        for(Point pt:this){
            word.append("[").append(pt.x).append(";").append(pt.y).append("]").append("\n");
        }
        return word.toString();
    }

}

