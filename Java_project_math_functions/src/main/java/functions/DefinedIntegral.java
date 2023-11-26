package functions;
import java.util.ArrayList;

public class DefinedIntegral implements MathFunction {
    public static final double INCREMENT = 1E-4;
    private final MathFunction f;

    private static double area = 0;

    DefinedIntegral(MathFunction H)
    {
        this.f = H;
    }

    public double integrate( double x)
    {
        double xFrom;
        double xTo;
        double modifier;
        if(x>0) {
            xFrom = 0;
            xTo = x;
            modifier =1.0;
        }
        else {
            xFrom = x;
            xTo = 0;
            modifier = -1.0;
        }
        for(double i=xFrom;i<xTo;i+=INCREMENT)
        {
            double dFromX = i - xFrom;
            area+= (INCREMENT/2)*(f.apply(xFrom+dFromX)+f.apply(xFrom+dFromX-INCREMENT));
        }
        return (Math.round(area*1000.0)/1000.0)*modifier;
    }
    public double apply(double x) {
        double res = integrate( x);
        area=0;
        return res;
    }
}
