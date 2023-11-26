package functions;
import java.lang.Math.*;

public class CosDoubleAcPlAs implements MathFunction {
    public double apply(double x) {
        return Math.cos(2*Math.acos(x)+2*Math.asin(x));
    }
}
