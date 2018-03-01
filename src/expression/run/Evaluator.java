package expression.run;

/**
 * @author Ilya Kokorin
 */

import expression.actions.Action;
import expression.exceptions.IncorrectActionException;
import expression.exceptions.OverflowException;
import expression.exceptions.ParsingException;
import expression.exceptions.WrongModeException;
import expression.parser.ExpressionParser;
import expression.parser.Parser;
import expression.terms.TripleExpression;

public class Evaluator {
    private static <T> T getResult(Action<T> action, String expression, String xValue, String yValue, String zValue)
            throws NumberFormatException, OverflowException, IncorrectActionException, ParsingException {
        T x = action.parse(xValue);
        T y = action.parse(yValue);
        T z = action.parse(zValue);
        Parser<T> parser = new ExpressionParser<>(action);
        TripleExpression<T> parsedExpression = parser.parse(expression);
        return parsedExpression.evaluate(x, y, z);
    }

    /**
    @param args evaluating mode, expression to parse, value of x, value of y, value of z
     */
    public static void main(String[] args) {
        if (args == null || args.length != 5 || args[0] == null || args[1] == null || args[2] == null ||
                args[3] == null || args[4] == null) {
            System.out.println("Incorrect input. Run:" +
                    "Evaluator <mode> <expression> <x value> <y value> <z value>");
            return;
        }
        Action<?> action;
        try {
            action = ModeGetter.getAction(args[0]);
        } catch (WrongModeException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            System.out.println("Result is " + getResult(action, args[1], args[2], args[3], args[4]));
        } catch (NumberFormatException e) {
            System.out.println("Incorrect number format " + e.getMessage());
        } catch (OverflowException e) {
            System.out.println("Overflow while evaluating " + e.getMessage());
        } catch (IncorrectActionException e) {
            System.out.println("Incorrect action " + e.getMessage());
        } catch (ParsingException e) {
            System.out.println("Unable to parse expression" + e.getMessage());
        }
    }
}
