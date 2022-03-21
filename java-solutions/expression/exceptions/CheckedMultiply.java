package expression.exceptions;

import expression.FullExpression;
import expression.Multiply;

public class CheckedMultiply extends Multiply {

    public CheckedMultiply(FullExpression ex1, FullExpression ex2) {
        super(ex1, ex2);
    }

    @Override
    protected int calc(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        if (a * b / b != a) {
            throw new IllegalArgumentException("Overflow");
        } else if (b * a / a != b) {
            throw new IllegalArgumentException("Overflow");
        }
        return a * b;
    }
}
