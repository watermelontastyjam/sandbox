package operations;

import functions.MathFunction;
import functions.SqrFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SteppingDifferentialOperatorTest {
    @Test
    public void deriveTest() {
        double STEP = 4;
        SteppingDifferentialOperator leftDifferentialOperator = new LeftSteppingDifferentialOperator(STEP);
        SteppingDifferentialOperator rightDifferentialOperator = new RightSteppingDifferentialOperator(STEP);
        SteppingDifferentialOperator middleDifferentialOperator = new MiddleSteppingDifferentialOperator(STEP);

        MathFunction sqrLeftDerive = leftDifferentialOperator.derive(new SqrFunction());
        MathFunction sqrRightDerive = rightDifferentialOperator.derive(new SqrFunction());
        MathFunction sqrMiddleDerive = middleDifferentialOperator.derive(new SqrFunction());

        assertEquals(196, sqrLeftDerive.apply(100), 0.001);
        assertEquals(204, sqrRightDerive.apply(100), 0.001);
        assertEquals(200, sqrMiddleDerive.apply(100), 0.001);
        assertEquals(20, sqrMiddleDerive.apply(10), 0.001);
    }
}