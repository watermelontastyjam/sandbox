package functions;
import java.util.Random;
import java.lang.*;

public class IdentityFunction implements MathFunction, Cloneable{
    public double apply(double x)
    {
        return x;
    }
    @Override
   public String toString()
    {
        return("данный метод реализован в классе IdentityFunction\n"+" Класс реализует интерфейсы MathFunction, Cloneable\n"+
                "Над объектами при вызове метода apply происходят тождественные преобразования\n" );
    }
    @Override
    public boolean equals(Object o)
    {
return((this==o)||(o!=null&&getClass()==o.getClass()));
    }
    @Override
    public int hashCode()
    {
        Random R1 = new Random(2^31-1);
        return R1.nextInt();
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();    // вернуть поверхностную копию
    }
}
