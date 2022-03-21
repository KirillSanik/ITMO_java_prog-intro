package expression;

import expression.BinaryOperation;
import expression.FullExpression;

public class Max extends BinaryOperation {

    public Max(FullExpression ex1, FullExpression ex2) {
        super(ex1, ex2);
    }

    protected String getSign() {
        return " max ";
    }

    protected int calc(int a, int b) {
        return Math.max(a, b);
    }
}