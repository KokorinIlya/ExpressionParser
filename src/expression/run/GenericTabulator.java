package expression.run;

import expression.actions.Action;
import expression.exceptions.IncorrectActionException;
import expression.exceptions.OverflowException;
import expression.exceptions.WrongModeException;
import expression.parser.ExpressionParser;
import expression.parser.Parser;
import expression.terms.TripleExpression;

/**
 * @author Ilya Kokorin
 */

public class GenericTabulator implements Tabulator {

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws WrongModeException {
        Action<?> action = ModeGetter.getAction(mode);
        return makeTable(action, expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] makeTable(Action<T> action, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] res = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        Parser<T> parser = new ExpressionParser<>(action);
        TripleExpression<T> exp;
        try {
            exp = parser.parse(expression);
        } catch (Exception e) {
            return res;
        }
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        res[i - x1][j - y1][k - z1] = exp.evaluate(action.parse(Integer.toString(i)), action.parse(Integer.toString(j)), action.parse(Integer.toString(k)));
                    } catch (IncorrectActionException | OverflowException e) {
                        res[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return res;
    }

}
