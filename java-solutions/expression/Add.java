package expression;

import java.math.BigInteger;

public class Add extends BinaryOperation {

    public Add(FullExpression ex1, FullExpression ex2) {
        super(ex1, ex2);
    }

    protected String getSign() {
        return " + ";
    }

    protected int calc(int a, int b) {
        return a + b;
    }
}
