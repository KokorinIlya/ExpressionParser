package expression.terms;

import expression.actions.Action;
import expression.exceptions.IncorrectActionException;
import expression.exceptions.OverflowException;

/**
 * @author Ilya Kokorin
 */
public class Mod<T> extends AbstractBinaryOperator<T> {
    public Mod(TripleExpression<T> first, TripleExpression<T> second, Action<T> action) {
        super(first, second, action);
    }

    protected T operation(T first, T second) throws OverflowException, IncorrectActionException {
        return action.mod(first, second);
    }
}
