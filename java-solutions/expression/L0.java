package expression;

public class L0 extends UnarOperation{

    public L0(FullExpression ex) {
        super(ex);
    }

    @Override
    public String toString() {
        return "l0(" + ex.toString() + ")";
    }

    @Override
    public int calc(int num) {
        if (num == 0) {
            return 32;
        } else if (num < 0) {
            return 0;
        } else {
            return 32 - Integer.toString(num, 2).length();
        }
    }
}
