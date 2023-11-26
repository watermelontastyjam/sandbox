package functions;

public class CompositeFunction implements MathFunction{
    private MathFunction firstFunction;
    private MathFunction secondFunction;
    public CompositeFunction(MathFunction f,MathFunction s)
    {
        this.firstFunction = f;
        this.secondFunction = s;

    }
    public double apply(double x)
    {
        return secondFunction.apply(firstFunction.apply(x));
    }
}
