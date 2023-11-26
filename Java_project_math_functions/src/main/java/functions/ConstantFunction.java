package functions;

public class ConstantFunction implements MathFunction{
   private final double x;
    public ConstantFunction(double a)
    {
        this.x=a;
    }

    double GetX()
    {
        return x;
    }

    public double apply(double x)
    {
        return GetX();
    }
}

