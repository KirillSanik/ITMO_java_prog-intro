package expression;

public class T0 extends UnarOperation{

    public T0(FullExpression ex) {
        super(ex);
    }

    @Override
    public String toString() {
        return "t0(" + ex.toString() + ")";
    }

    @Override
    public int calc(int num) {
        int cnt = 0;
        while(num % 2 == 0 && cnt < 32) {
            cnt++;
            num = num / 2;
        }
        return cnt;
    }
}
