package functions;

public class ZeroFunction implements MathFunction{
    final private double x =0;

    double GetX()
    {
        return x;
    }
    public double apply(double x){
        return GetX();
    }
}
