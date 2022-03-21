package expression;

import expression.BinaryOperation;
import expression.FullExpression;

public class Min extends BinaryOperation {

    public Min(FullExpression ex1, FullExpression ex2) {
        super(ex1, ex2);
    }

    protected String getSign() {
        return " min ";
    }

    protected int calc(int a, int b) {
        return Math.min(a, b);
    }
}
