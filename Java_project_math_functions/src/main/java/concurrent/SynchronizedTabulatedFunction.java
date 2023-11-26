package concurrent;

import functions.LinkedListTabulatedFunction;
import functions.Point;
import functions.TabulatedFunction;
import operations.TabulatedFunctionOperationService;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SynchronizedTabulatedFunction implements TabulatedFunction {
    private final TabulatedFunction func;

    public SynchronizedTabulatedFunction(TabulatedFunction f){
        this.func = f;
    }
    @Override
    public int getCount(){
        synchronized (func) {
            return func.getCount();
        }
    }
    @Override
    public double getX(int index) throws IllegalArgumentException
    {
        synchronized (func){
            return func.getX(index);
        }
    }
    @Override
    public double getY(int index) throws IllegalArgumentException
    {
        synchronized (func){
            return func.getY(index);
        }
    }
    @Override
    public void setY(int index,double value) throws IllegalArgumentException
    {
        synchronized (func){
            func.setY(index, value);
        }
    }
    @Override
    public int indexOfX(double x) throws IllegalArgumentException
    {
        synchronized (func){
            return func.indexOfX(x);
        }
    }
    @Override
    public int indexOfY(double y) throws IllegalArgumentException
    {
        synchronized (func){
            return func.indexOfY(y);
        }
    }
    @Override
    public double leftBound(){
        synchronized (func){
            return func.leftBound();
        }
    }
    @Override
    public double rightBound(){
        synchronized (func){
            return func.rightBound();
        }
    }

    @Override
    public double apply(double x) {
        synchronized (func){
            return func.apply(x);
        }
    }

    @Override
    public Iterator<Point> iterator() {

        synchronized(func){
            Point[] points;
            points = TabulatedFunctionOperationService.asPoints(func);
            return new Iterator<Point>() {
                int indexOfCurPoint = 0;
                @Override
                public boolean hasNext() {
                    return (indexOfCurPoint != points.length);
                }

                @Override
                public Point next() {
                    if(hasNext()){
                        indexOfCurPoint++;
                        return points[indexOfCurPoint];
                    }else throw new NoSuchElementException();
                }
            };

        }
    }
}

