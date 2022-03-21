package expression;

public abstract class BinaryOperation implements FullExpression {

    protected final FullExpression ex1;
    protected final FullExpression ex2;

    protected BinaryOperation(FullExpression ex1, FullExpression ex2) {
        this.ex1 = ex1;
        this.ex2 = ex2;
    }

    protected FullExpression getEx1() {
        return ex1;
    }

    protected FullExpression getEx2() {
        return ex2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            return ex1.equals(((BinaryOperation) obj).getEx1())
                    && ex2.equals(((BinaryOperation) obj).getEx2());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ex1.hashCode() * 3 + ex2.hashCode() * 7 + this.getClass().getName().hashCode();
    }


    public int evaluate(int x) {
        return calc(ex1.evaluate(x), ex2.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return calc(ex1.evaluate(x, y, z), ex2.evaluate(x, y, z));
    }

    public String toString() {
            return "(" + ex1.toString() + getSign()  + ex2.toString() + ")";
    }

    protected abstract int calc(int a, int b);

    protected abstract String getSign();
}
