package expression.terms;

import expression.actions.Action;
import expression.exceptions.IncorrectActionException;
import expression.exceptions.OverflowException;

/**
 * @author Ilya Kokorin
 */
public class Negate<T> extends AbstractUnaryOperator<T> {
    public Negate(TripleExpression<T> expression, Action<T> action) {
        super(expression, action);
    }

    protected T operation(T operand) throws OverflowException, IncorrectActionException {
        return action.negate(operand);
    }
}
