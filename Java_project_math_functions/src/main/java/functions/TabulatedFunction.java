package functions;

import java.util.Iterator;

public interface TabulatedFunction extends MathFunction,Iterable<Point> {
    int getCount();
    double getX(int index) throws IllegalArgumentException;
    double getY(int index) throws IllegalArgumentException;
    void setY(int index, double value) throws IllegalArgumentException;
    int indexOfX(double x) throws IllegalArgumentException;
    int indexOfY(double y) throws IllegalArgumentException;
    double leftBound() ;
    double rightBound();

}
