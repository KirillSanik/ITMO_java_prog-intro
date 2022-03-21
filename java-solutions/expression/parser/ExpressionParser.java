package expression.parser;

import expression.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpressionParser implements Parser {

    private static final Map<Character, Integer> CHAR_TO_PRIORITY = Map.of(
            'a', 0,
            'i', 0,
            '+', 1,
            '-', 1,
            '*', 2,
            '/', 2
    );

    private int globCnt = 0;

    private FullExpression createNum(String num) {
        if (num.equals("+2147483648")) {
            return new oneExpression(new Const(Integer.parseInt("-2147483648")), '-');
        } else {
            return new Const(Integer.parseInt(num));
        }
    }

    private Character getUnarOp(Character unarOp, char currUnarOp) {
        if (unarOp != null && unarOp == '-' && currUnarOp == '-')
            return '+';
        else {
            return currUnarOp;
        }
    }

    private FullExpression prParse(String expression) {
        FullExpression ex1 = null;
        FullExpression ex2 = null;

        Character operation = null;
        Character unarOperation = null;

        List<FullExpression> exs = new ArrayList<>();
        List<Character> operations = new ArrayList<>();

        int currCnt = 0;
        for (int i = 0; i < expression.length() && expression.charAt(i) != ')'; i++, currCnt++) {
            if ('0' <= expression.charAt(i) && expression.charAt(i) <= '9') {
                int currI = i;
                while (i < expression.length() && '0' <= expression.charAt(i) && expression.charAt(i) <= '9') {
                    i++;
                }
                String constTemp = expression.substring(currI, i);
                if (unarOperation != null) {
                    constTemp = unarOperation.toString() + constTemp;
                    unarOperation = null;
                }
                if (ex1 == null) {
                    ex1 = createNum(constTemp);
                } else {
                    ex2 = createNum(constTemp);
                }
                i--;
            } else {
                switch (expression.charAt(i)) {
                    case 'm':
                        exs.add(ex1);
                        ex1 = null;
                        operations.add(expression.charAt(++i));
                        i++;
                        break;
                    case '-':
                    case '+':
                        if (operation == null) {
                            if (ex1 != null) {
                                exs.add(ex1);
                                operations.add(expression.charAt(i));
                                ex1 = null;
                            } else {
                                unarOperation = getUnarOp(unarOperation, expression.charAt(i));
                            }
                        } else {
                            unarOperation = getUnarOp(unarOperation, expression.charAt(i));
                        }
                        break;
                    case '*':
                    case '/':
                        operation = expression.charAt(i);
                        break;
                    case 'x':
                    case 'y':
                    case 'z':
                        if (ex1 == null) {
                            ex1 = new Variable(expression.substring(i, i + 1));
                            if (unarOperation != null && unarOperation == '-') {
                                ex1 = new oneExpression(ex1, unarOperation);
                                unarOperation = null;
                            }
                        } else {
                            ex2 = new Variable(expression.substring(i, i + 1));
                            if (unarOperation != null && unarOperation == '-') {
                                ex2 = new oneExpression(ex2, unarOperation);
                                unarOperation = null;
                            }
                        }
                        break;
                    case '(':
                        int currSk = ++i;
                        FullExpression tempEx = prParse(expression.substring(currSk));
                        i += globCnt;

                        if (ex1 == null) {
                            ex1 = tempEx;
                            if (unarOperation != null && unarOperation == '-') {
                                ex1 = new oneExpression(ex1, unarOperation);
                                unarOperation = null;
                            }
                        } else {
                            ex2 = tempEx;
                            if (unarOperation != null && unarOperation == '-') {
                                ex2 = new oneExpression(ex2, unarOperation);
                                unarOperation = null;
                            }
                        }
                        break;
                    default:
                        break;
                }
            }

            if (ex1 != null && ex2 != null) {
                ex1 = createEx(ex1, ex2, operation);
                ex2 = null;
                operation = null;
                if (unarOperation != null && unarOperation == '-') {
                    ex1 = new oneExpression(ex1, unarOperation);
                }
                unarOperation = null;
            }
            currCnt = i;
        }
        globCnt = currCnt;

        if (exs.size() == 0) {
            //System.err.println(ex1.toString());
            return ex1;
        } else {
            exs.add(ex1);
            //System.err.println(exs + " " + operations);
            //System.err.println(priorityElem(exs, operations, 1));
            return priorityElem(exs, operations, 1);
        }
    }

    private FullExpression priorityElem(List<FullExpression> exs, List<Character> operations, int currPriority) {
        FullExpression resEx = null;

        List<FullExpression> secExs = new ArrayList<>();
        List<Character> secOperations = new ArrayList<>();

        for (int i = 0; i < exs.size() - 1; i++) {
            int currOpPriority = CHAR_TO_PRIORITY.get(operations.get(i));
            if (currOpPriority == currPriority) {
                if (resEx != null) {
                    resEx = createEx(resEx, exs.get(i + 1), operations.get(i));
                } else {
                    resEx = createEx(exs.get(i), exs.get(i + 1), operations.get(i));
                }
                if (i == operations.size() - 1 && secExs.size() > 0) {
                    secExs.add(resEx);
                }
            } else {
                if (resEx != null) {
                    secExs.add(resEx);
                    resEx = null;
                } else {
                    secExs.add(exs.get(i));
                }
                secOperations.add(operations.get(i));
                if (i == operations.size() - 1) {
                    secExs.add(exs.get(i + 1));
                }
            }
        }
        //System.err.println(secExs + " " + secOperations);
        if (secExs.size() > 0) {
            resEx = priorityElem(secExs, secOperations, currPriority - 1);
        }
        return  resEx;
    }

    private FullExpression createEx(FullExpression ex1, FullExpression ex2, char oper) {
        return switch (oper) {
            case '+' -> new Add(ex1, ex2);
            case '-' -> new Subtract(ex1, ex2);
            case '*' -> new Multiply(ex1, ex2);
            case '/' -> new Divide(ex1, ex2);
            case 'a' -> new Max(ex1, ex2);
            default -> new Min(ex1, ex2);
        };
    }

    @Override
    public TripleExpression parse(String expression) {
        /*System.err.println("***");
        System.err.println(expression);
        System.err.println(prParse(expression).toString());
        System.err.println("***");*/
        return prParse(expression);
    }
}
