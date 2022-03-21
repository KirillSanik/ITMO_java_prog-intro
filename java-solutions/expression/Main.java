package expression;

public class Main {

    public static void main(String[] args) {
        int res = new Subtract(new Multiply(new Variable("x"), new Variable("x"))
        , new Subtract(new Multiply(new Const(2), new Variable("x"))
        , new Const(1))).evaluate(Integer.parseInt(args[0]));
        System.out.println(res);
    }
}
