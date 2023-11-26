package functions;

import java.awt.*;
import java.util.Iterator;

public class StrictTabulatedFunction implements TabulatedFunction{

    TabulatedFunction function;
    StrictTabulatedFunction (TabulatedFunction func) {
        this.function = func;
    }
    public double apply(double x) throws UnsupportedOperationException {
        int i = indexOfX(x);
        if(i == -1)
            throw new UnsupportedOperationException("There is no x = " + x +" !");
        else
            return getY(i);

}

    public int getCount() {
        return function.getCount();
    }

    public double getX(int index) throws IllegalArgumentException {
        return function.getX(index);
    }

    public double getY(int index) throws IllegalArgumentException {
        return function.getY(index);
    }

    public void setY(int index, double value) throws IllegalArgumentException {
        function.setY(index, value);
    }

    public int indexOfX(double x) throws IllegalArgumentException {
        return function.indexOfX(x);
    }

    public int indexOfY(double y) throws IllegalArgumentException {
        return function.indexOfY(y);
    }

    public double leftBound() {
        return function.leftBound();
    }

    public double rightBound() {
        return function.rightBound();
    }

    public Iterator<Point> iterator() {
        return function.iterator();
    }
}
