package expression.parser;

import expression.*;
import expression.exceptions.CheckedNegate;

public class oneExpression implements FullExpression {

    private final UnarOperation UnarOp;

    public oneExpression(FullExpression ex, char znak) {
        switch (znak) {
            case '-' -> UnarOp = new Negate(ex);
            case '+' -> UnarOp = new Plus(ex);
            case 'l' -> UnarOp = new L0(ex);
            case 't' -> UnarOp = new T0(ex);
            default -> UnarOp = new Plus(ex);
        }
    }

    @Override
    public int evaluate(int x) {
        return UnarOp.evaluate(x);
    }

    @Override
    public String toString() {
        return UnarOp.toString();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return UnarOp.evaluate(x, y, z);
    }
}
