package functions;

public class LimitFunction implements MathFunction{

    private final MathFunction f;
    LimitFunction(MathFunction func)
    {
        this.f = func;
    }

    private double lim(double a)
    {
        double sig = 0.000000001;
        double x = a - sig;
        double prevRes = Double.POSITIVE_INFINITY;
        double res = f.apply(x);
        double eps = 0.000000001;

        while(Math.abs(res - prevRes) < eps)
        {
            prevRes = res;
            x = refineX(x,a);
            res = f.apply(x);
        }
        return res;
    }
    private double refineX(double x,double a){return (x+a)/2;}
    public double apply(double a){
        return lim(a);
    }

}
