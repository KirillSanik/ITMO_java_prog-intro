package expression;

public class Negate extends UnarOperation{

    public Negate(FullExpression ex) {
        super(ex);
    }

    @Override
    public String toString() {
        return "-(" + ex.toString() + ")";
    }

    @Override
    public int calc(int num) {
        return -num;
    }
}
