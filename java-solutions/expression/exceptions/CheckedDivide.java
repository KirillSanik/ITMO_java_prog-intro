package expression.exceptions;

import expression.Divide;
import expression.FullExpression;

public class CheckedDivide extends Divide {
    public CheckedDivide(FullExpression ex1, FullExpression ex2) {
        super(ex1, ex2);
    }

    @Override
    protected int calc(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero");
        }
        if (b == -1 && a == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Overflow");
        }
        return a / b;
    }
}
