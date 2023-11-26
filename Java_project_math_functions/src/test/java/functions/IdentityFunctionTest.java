package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentityFunctionTest {

    IdentityFunction i = new IdentityFunction();
    @Test
    void apply() {
        assertAll(
                ()-> assertEquals(6.3290, i.apply(6.3290)),
                ()-> assertEquals(-4.9320d, i.apply(-4.9320d)),
                ()-> assertEquals(0, i.apply(0)));
    }

    @Test
    void toStringTest(){
        String class_description1 = new String();
        class_description1 = "данный метод реализован в классе IdentityFunction\n";
        String class_description2 = new String();
        class_description2 = " Класс реализует интерфейсы MathFunction, Cloneable\n";
        String class_description3 = new String();
        class_description3 ="Над объектами при вызове метода apply происходят тождественные преобразования\n";
        String class_description = new String();
        class_description = class_description1+class_description2+class_description3;
        assertTrue(class_description.equals("данный метод реализован в классе IdentityFunction\n"+" Класс реализует интерфейсы MathFunction, Cloneable\n"+ "Над объектами при вызове метода apply происходят тождественные преобразования\n"));
    }

    @Test
    void equalsTest()
    {
        IdentityFunction iF1 = new IdentityFunction();
        IdentityFunction iF2 = new IdentityFunction();
        assertTrue(iF1.equals(iF2));
    }

    @Test
    void hashCodeTest()
    {
        IdentityFunction iF1 = new IdentityFunction();
        IdentityFunction iF2 = new IdentityFunction();
        int expected = iF1.hashCode();
        int actual = iF2.hashCode();
        assertEquals(expected, actual);
    }

    @Test
    void cloneTest() throws CloneNotSupportedException {
        IdentityFunction iF1 = new IdentityFunction();
        assertTrue(iF1.equals(iF1.clone()));
    }
}