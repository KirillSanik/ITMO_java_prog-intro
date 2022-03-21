package expression.exceptions;

import expression.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// :NOTE: указывать место ошибки парсинга
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
    private int skobPos;

    // :NOTE: управление на исключениях
    private FullExpression createNum(String num) {
        try {
            return new Const(Integer.parseInt(num));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("overflow");
        }
    }

    private FullExpression getExp(FullExpression ex, StringBuilder unarOp) {
        FullExpression res = ex;
        for (int i = unarOp.length() - 1; i >= 0; i--) {
            if (unarOp.charAt(i) == '+') {
                continue;
            } else {
                res = new oneExpression(res, unarOp.charAt(i));
            }
        }
        return res;
    }

    private boolean isValidSymb(char symb) {
        return Character.isWhitespace(symb) || symb == '(' || symb == ')' || symb == '-' || symb == '+';
    }

    // :NOTE: не надо указывать RuntimeException
    private FullExpression prParse(String expression) throws IllegalArgumentException, ParseException {
        //System.err.println("start");
        FullExpression ex1 = null;
        FullExpression ex2 = null;

        Character operation = null;
        StringBuilder unarOperation = new StringBuilder("");

        List<FullExpression> exs = new ArrayList<>();
        List<Character> operations = new ArrayList<>();

        int currCnt = 0;
        for (int i = 0; i < expression.length() && expression.charAt(i) != ')'; i++, currCnt++) {
            //System.err.print(expression.charAt(i));
            if ('0' <= expression.charAt(i) && expression.charAt(i) <= '9') {
                int currI = i;
                while (i < expression.length() && '0' <= expression.charAt(i) && expression.charAt(i) <= '9') {
                    i++;
                }
                String constTemp = expression.substring(currI, i);
                //int cnt = 0;
                /*for (int j = unarOperation.length() - 1; j >= 0; j--) {
                    if (unarOperation.charAt(j) == '-') {
                        cnt++;
                    } else if (unarOperation.charAt(j) == '+') {
                        continue;
                    } else {
                        break;
                    }
                }
                unarOperation = new StringBuilder(unarOperation.substring(0, unarOperation.length() - cnt));*/
                /*if (cnt % 2 == 1) {
                    constTemp = "-" + constTemp;
                }*/
                FullExpression tempC;
                if (unarOperation.length() > 0 && unarOperation.charAt(unarOperation.length() - 1) == '-') {
                    tempC = createNum('-' + constTemp);
                    tempC = getExp(tempC, new StringBuilder(unarOperation.substring(0, unarOperation.length() - 1)));
                } else {
                    tempC = createNum(constTemp);
                    tempC = getExp(tempC, unarOperation);
                }
                if (ex1 == null) {
                    //ex1 = createNum(constTemp);
                    //ex1 = getExp(ex1, unarOperation);
                    //сhekOp(operation);
                    ex1 = tempC;
                } else {
                    //ex2 = createNum(constTemp);
                    //ex2 = getExp(ex2, unarOperation);
                    ex2 = tempC;
                }
                unarOperation = new StringBuilder("");
                i--;
            } else {
                switch (expression.charAt(i)) {
                    case 'l':
                    case 't':
                        try {
                            if (!(i < 1 || isValidSymb(expression.charAt(i - 1))) || !isValidSymb(expression.charAt(i + 2))) {
                                throw new ParseException("Неправильно стоит унарная операция", 0);
                            }
                        } catch (IndexOutOfBoundsException e) {
                            throw new ParseException("ошибка с L0 T0", 0);
                        }
                        unarOperation.append(expression.charAt(i));
                        i++;
                        break;
                    case 'm':
                        try {
                            if (!(isValidSymb(expression.charAt(i - 1))) || !isValidSymb(expression.charAt(i + 3))) {
                                throw new ParseException("Неправильно стоит операция", 0);
                            }
                        } catch (IndexOutOfBoundsException e) {
                            throw new ParseException("Ошибка с MinMax", 0);
                        }
                        if (ex1 == null) {
                            throw new ParseException("Нету первого аргумента", 0);
                        }
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
                                if (expression.charAt(i) == '+') {
                                    throw new ParseException("неверный унарный знак", 0);
                                }
                                unarOperation.append(expression.charAt(i));
                            }
                        } else {
                            if (expression.charAt(i) == '+') {
                                throw new ParseException("неверный унарный знак", 0);
                            }
                            unarOperation.append(expression.charAt(i));
                        }
                        break;
                    case '*':
                    case '/':
                        if (operation != null || ex1 == null) {
                            throw new ParseException("вторая подряд операция", 0);
                        }
                        operation = expression.charAt(i);
                        break;
                    case 'x':
                    case 'y':
                    case 'z':
                        if (ex1 == null) {
                            //сhekOp(operation);
                            ex1 = new Variable(expression.substring(i, i + 1));
                            ex1 = getExp(ex1, unarOperation);
                        } else {
                            ex2 = new Variable(expression.substring(i, i + 1));
                            ex2 = getExp(ex2, unarOperation);
                        }
                        unarOperation = new StringBuilder("");
                        break;
                    case '(':
                        skobPos++;
                        int currSk = ++i;
                        FullExpression tempEx = prParse(expression.substring(currSk));
                        i += globCnt;

                        if (tempEx == null) {
                            throw new ParseException("Строчка без аргументов", 0);
                        }

                        if (ex1 == null) {
                            ex1 = tempEx;
                            ex1 = getExp(ex1, unarOperation);
                        } else {
                            ex2 = tempEx;
                            ex2 = getExp(ex2, unarOperation);
                        }
                        unarOperation = new StringBuilder("");
                        break;
                    default:
                        if (!Character.isWhitespace(expression.charAt(i))) {
                            //System.err.print("errSymb");
                            throw new ParseException("неверный символ", 0);
                        }
                        break;
                }
            }

            if (ex1 != null && ex2 != null) {
                if (operation == null) {
                    throw new ParseException("(((((", 0);
                }
                ex1 = createEx(ex1, ex2, operation);
                ex2 = null;
                operation = null;
                ex1 = getExp(ex1, unarOperation);
                unarOperation = new StringBuilder("");
            }
            currCnt = i;
        }
        if (currCnt < expression.length() && expression.charAt(currCnt) == ')') {
            skobPos--;
        }
        globCnt = currCnt;
        //System.err.println(ex1 + " " + operation + ":" + unarOperation + ":");
        if (ex1 != null && ex2 == null && operation != null) {
            throw new ParseException("Лишняя операция", 0);
        }

        //System.err.println("end");
        if (exs.size() == 0) {
            //System.err.println(ex1.toString());
            return ex1;
        } else {
            exs.add(ex1);
            if (ex1 == null) {
                throw new ParseException("Лишняя операция", 0);
            }
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
            case '+' -> new CheckedAdd(ex1, ex2);
            case '-' -> new CheckedSubtract(ex1, ex2);
            case '*' -> new CheckedMultiply(ex1, ex2);
            case '/' -> new CheckedDivide(ex1, ex2);
            case 'a' -> new Max(ex1, ex2);
            default -> new Min(ex1, ex2);
        };
    }

    @Override
    public TripleExpression parse(String expression) throws IllegalArgumentException, ParseException {
        /*String test = new String(" min 1");
        TripleExpression testR = prParse(test);
        System.err.println("dsfdf");
        System.err.println(testR);
        System.err.println("dsfdf");*/
        //System.err.println("\n***S");
        //System.err.println(expression);
        //System.err.println(":");
        /*System.err.println(prParse(expression).toString());
        System.err.println("***E");*/
        skobPos = 0;
        TripleExpression res = prParse(expression);
        //System.err.println("\n" + res.toString());
        //System.err.println(":"+skobPos);
        if (res == null) {
            throw new ParseException("Строчка без аргументов", 0);
        }
        if (skobPos != 0) {
            throw new ParseException("Неправильная скобочная последовательность", 0);
        }
        return res;
    }
}
