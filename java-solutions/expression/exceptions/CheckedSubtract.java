package expression.exceptions;

import expression.FullExpression;
import expression.Subtract;

public class CheckedSubtract extends Subtract {

    public CheckedSubtract(FullExpression ex1, FullExpression ex2) {
        super(ex1, ex2);
    }

    @Override
    protected int calc(int a, int b) {
        //System.err.println(a + " " + b);
        if (a >= 0 && b < 0 && a - b < 0) {
            throw new IllegalArgumentException("Overflow");
        } else if (a < 0 && b > 0 && a - b > 0) {
            throw new IllegalArgumentException("Overflow");
        }
        return a - b;
    }
}
