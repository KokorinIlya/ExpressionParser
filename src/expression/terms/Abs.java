package expression.terms;

import expression.actions.Action;
import expression.exceptions.IncorrectActionException;
import expression.exceptions.OverflowException;

/**
 * @author Ilya Kokorin
 */
public class Abs<T> extends AbstractUnaryOperator<T> {
    public Abs(TripleExpression<T> expression, Action<T> action) {
        super(expression, action);
    }

    protected T operation(T operand) throws OverflowException, IncorrectActionException {
        return action.abs(operand);
    }
}
