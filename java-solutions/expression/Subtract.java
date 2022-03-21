package expression;

public class Subtract extends BinaryOperation {

    public Subtract(FullExpression ex1, FullExpression ex2) {
        super(ex1, ex2);
    }


    protected String getSign() {
        return " - ";
    }

    protected int calc(int a, int b) {
        return a - b;
    }
}
