package functions;

public class AcosPlusAsin implements MathFunction {
    public double apply(double x) {
        return Math.asin(x)+Math.acos(x);
    }
}
