package expression.parser;

import java.util.HashMap;

import expression.actions.Action;
import expression.exceptions.ParsingException;
import expression.terms.Abs;
import expression.terms.Add;
import expression.terms.Const;
import expression.terms.Div;
import expression.terms.Mod;
import expression.terms.Mul;
import expression.terms.Negate;
import expression.terms.Square;
import expression.terms.Sub;
import expression.terms.TripleExpression;
import expression.terms.Variable;

/**
 * @author Ilya Kokorin
 */
public class ExpressionParser<T> implements Parser<T> {
    private String expression;
    private int index;

    private static enum AllTokens {
        CONSTANT, VARIABLE,
        UNARY_MINUS, ABS, SQR,
        ADD, SUB,
        MUL, DIV, MOD,
        LEFT_BRACKET, RIGHT_BRACKET,
        BEGIN, END
    }

    private static HashMap<String, AllTokens> stringToToken = new HashMap<String, AllTokens>();

    static {
        stringToToken.put("abs", AllTokens.ABS);
        stringToToken.put("square", AllTokens.SQR);
        stringToToken.put("mod", AllTokens.MOD);
        stringToToken.put("x", AllTokens.VARIABLE);
        stringToToken.put("y", AllTokens.VARIABLE);
        stringToToken.put("z", AllTokens.VARIABLE);
    }

    private AllTokens currentToken;

    private T constantValue;
    private char variableName;
    private int balance;
    private Action<T> action;

    private boolean isInIdentifier(char c){
        return (Character.isLetter(c) || Character.isDigit(c) || c == '_');
    }

    private AllTokens parseIdentifier(int l) throws ParsingException{
        if (!Character.isLetter(expression.charAt(l))){
            throw new ParsingException("Unknown symbol at ", expression, index);
        }
        while (index < expression.length() &&
                isInIdentifier(expression.charAt(index))) {
            index++;
        }
        int r = index;
        String identifier = expression.substring(l, r);
        if (stringToToken.containsKey(identifier)) {
            index--;
            return stringToToken.get(identifier);
        } else {
            throw new ParsingException("Unknown identifier at ", expression, l);
        }
    }

    private boolean isInConst(char c) {
        return Character.isDigit(c) || c == '.' || c == 'e' || c == 'E' || c == '-';
    }

    private void parseConst(int l) throws ParsingException{
        while (index < expression.length() && isInConst(expression.charAt(index))) {
            index++;
        }
        int r = index;
        try {
            constantValue = action.parse(expression.substring(l, r));
        } catch (NumberFormatException e) {
            throw new ParsingException("Unparsable constant ", expression, l);
        }
        currentToken = AllTokens.CONSTANT;
        index--;
    }

    private void skipWhiteSpace() {
        while (index < expression.length() &&
                Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    private void checkOperand() throws ParsingException {
        if (currentToken != AllTokens.RIGHT_BRACKET
                && currentToken != AllTokens.VARIABLE && currentToken != AllTokens.CONSTANT) {
            throw new ParsingException("Missing operand at ", expression, index);
        }
    }

    private void checkOperation() throws ParsingException {
        if (currentToken == AllTokens.RIGHT_BRACKET ||
                currentToken == AllTokens.VARIABLE || currentToken == AllTokens.CONSTANT) {
            throw new ParsingException("Missing operation at ", expression, index);
        }
    }

    /*private void checkEnd() throws ParsingException {
        if (index + 1 >= expression.length()) {
            throw new ParsingException("Missing operand at ", expression, index);
        }
    }*/

    private void nextToken() throws ParsingException {
        skipWhiteSpace();
        if (index >= expression.length()) {
            if (balance > 0)
            {
                throw new ParsingException("Missing closing bracket at ", expression, index);
            }
            checkOperand();
            currentToken = AllTokens.END;
            return;
        }
        char currentChar = expression.charAt(index);
        switch (currentChar) {
            case '-':
                if (currentToken == AllTokens.CONSTANT ||
                        currentToken == AllTokens.VARIABLE ||
                        currentToken == AllTokens.RIGHT_BRACKET) {
                    checkOperand();
                    currentToken = AllTokens.SUB;
                } else {
                    //checkEnd();
                    if (Character.isDigit(expression.charAt(index + 1))) {
                        index++;
                        parseConst(index - 1);
                    } else {
                        currentToken = AllTokens.UNARY_MINUS;
                    }
                }
                break;
            case '+':
                checkOperand();
                //checkEnd();
                currentToken = AllTokens.ADD;
                break;
            case '*':
                checkOperand();
                //checkEnd();
                currentToken = AllTokens.MUL;
                break;
            case '/':
                checkOperand();
                //checkEnd();
                currentToken = AllTokens.DIV;
                /*if (expression.charAt(index + 1) == '/') {
                    currentToken = AllTokens.LOG;
                } else {
                    currentToken = AllTokens.DIV;
                }*/
                break;
            case '(':
                if (currentToken == AllTokens.RIGHT_BRACKET ||
                        currentToken == AllTokens.CONSTANT ||
                        currentToken == AllTokens.VARIABLE) {
                    throw new ParsingException("Unexpected opening bracket at ", expression, index);
                }
                balance++;
                currentToken = AllTokens.LEFT_BRACKET;
                break;
            case ')':
                if (currentToken != AllTokens.VARIABLE &&
                        currentToken != AllTokens.CONSTANT && currentToken != AllTokens.RIGHT_BRACKET) {
                    throw new ParsingException("Unexpected closing bracket at ", expression, index);
                }
                balance--;
                if (balance < 0) {
                    throw new ParsingException("Extra closing bracket at ", expression, index);
                }
                currentToken = AllTokens.RIGHT_BRACKET;
                break;
            default:
                if (Character.isDigit(currentChar)) {
                    checkOperation();
                    parseConst(index);
                } else {
                    int begin = index;
                    int save;
                    AllTokens nextToken = parseIdentifier(index);
                    if (nextToken == AllTokens.VARIABLE){
                        checkOperation();
                        variableName = expression.charAt(index);
                    }
                    currentToken = nextToken;
                }
        }
        index++;
    }

    private TripleExpression<T> unary() throws ParsingException {
        nextToken();
        TripleExpression<T> res;
        switch (currentToken) {
            case CONSTANT:
                res = new Const<T>(constantValue);
                nextToken();
                break;
            case VARIABLE:
                res = new Variable<T>(Character.toString(variableName));
                nextToken();
                break;
            case UNARY_MINUS:
                res = new Negate<T>(unary(), action);
                break;
            case ABS:
                res = new Abs<T>(unary(), action);
                break;
            case SQR:
                res = new Square<T>(unary(), action);
                break;
            /*case SQRT:
                res = new CheckedSqrt(unary());
                break;*/
            case LEFT_BRACKET:
                res = addSub();
                nextToken();
                break;
            default:
                throw new ParsingException("SOMETHING WENT WRONG at ", expression, index);
        }
        return res;
    }

    private TripleExpression<T> mulDivMod() throws ParsingException {
        TripleExpression<T> res = unary();
        while (true) {
            switch (currentToken) {
                case MUL:
                    res = new Mul<T>(res, unary(), action);
                    break;
                case DIV:
                    res = new Div<T>(res, unary(), action);
                    break;
                case MOD:
                    res = new Mod<T>(res, unary(), action);
                    break;
                default:
                    return res;
            }
        }
    }

    private TripleExpression<T> addSub() throws ParsingException {
        TripleExpression<T> res = mulDivMod();
        while (true) {
            switch (currentToken) {
                case ADD:
                    res = new Add<T>(res, mulDivMod(), action);
                    break;
                case SUB:
                    res = new Sub<T>(res, mulDivMod(), action);
                    break;
                default:
                    return res;
            }
        }
    }

    /*private TripleExpression minMax() throws ParsingException {
        TripleExpression operand = addSub();
        while (true) {
            switch (currentToken) {
                case MIN:
                    operand = new CheckedMin(operand, addSub());
                    break;
                case MAX:
                    operand = new CheckedMax(operand, addSub());
                    break;
                default:
                    return operand;
            }
        }
    }*/

    public TripleExpression<T> parse(String expression) throws ParsingException {
        index = 0;
        balance = 0;
        currentToken = AllTokens.BEGIN;
        this.expression = expression;
        return addSub();
    }

    public ExpressionParser(Action<T> action) {
        this.action = action;
    }
}
