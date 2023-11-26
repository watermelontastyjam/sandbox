package functions;

import java.awt.*;
import java.util.Iterator;

public class MockTabulatedFunction extends AbstractTabulatedFunction {
    private double[] xValue;
    private double[] yValue;

    public MockTabulatedFunction() {
        xValue = new double[]{0, 1};
        yValue = new double[]{0, 1};
        count = 2;
    }

    protected int floorIndexOfX(double x) {
        if (x < 0)
            return -1;
        else if (x < 1)
            return 0;
        else
            return 1;
    }

    protected double extrapolateLeft(double x) {
        return interpolate(x, floorIndexOfX(0));
    }

    protected double extrapolateRight(double x) {
        return interpolate(x, floorIndexOfX(0));
    }

    protected double interpolate(double x, int floorIndex) {
        return interpolate(x, getX(floorIndex), getX(floorIndex + 1), getY(floorIndex), getY(floorIndex + 1));
    }

    public int getCount() {
        return count;
    }

    public double getX(int index) {
        return xValue[index];
    }

    public double getY(int index) {
        return yValue[index];
    }

    public void setY(int index, double value) {
        yValue[index] = value;
    }

    public int indexOfX(double x) {
        if (x == 0)
            return 0;
        else if (x == 1)
            return 1;
        else
            return -1;
    }

    public int indexOfY(double y) {
        if (y == 0)
            return 0;
        else if (y == 1)
            return 1;
        else
            return -1;
    }

    public double leftBound() {
        return 0;
    }

    public double rightBound() {
        return 1;
    }
    @Override
    public Iterator<Point> iterator()
    {
        return null;
    }
}
