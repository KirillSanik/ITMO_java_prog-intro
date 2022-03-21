package expression;

public class Plus extends UnarOperation{

    public Plus(FullExpression ex) {
        super(ex);
    }

    @Override
    public String toString() {
        return "(" + ex.toString() + ")";
    }

    @Override
    public int calc(int num) {
        return num;
    }
}
