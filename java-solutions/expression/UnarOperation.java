package expression;

public abstract class UnarOperation implements FullExpression {
    protected final FullExpression ex;

    public UnarOperation(FullExpression ex) {
        this.ex = ex;
    }

    public abstract String toString();

    public abstract int calc(int num);

    public int evaluate(int x) {
        return calc(ex.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return calc(ex.evaluate(x, y, z));
    }
}
