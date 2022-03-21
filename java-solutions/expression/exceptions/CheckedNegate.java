package expression.exceptions;

import expression.FullExpression;
import expression.Negate;

public class CheckedNegate extends Negate {

    public CheckedNegate(FullExpression ex) {
        super(ex);
    }

    @Override
    public int calc(int num) {
        if (num == -2147483648) {
            throw new IllegalArgumentException("Overflow");
        }
        return -num;
    }
}
