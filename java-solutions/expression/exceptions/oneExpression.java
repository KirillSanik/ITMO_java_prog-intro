package expression.exceptions;

import expression.*;
import expression.exceptions.CheckedNegate;

public class oneExpression implements FullExpression {

    private final UnarOperation UnarOp;

    // :NOTE: проверки по первому знаку недостаточно
    public oneExpression(FullExpression ex, char znak) {
        switch (znak) {
            case '-' -> UnarOp = new CheckedNegate(ex);
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
