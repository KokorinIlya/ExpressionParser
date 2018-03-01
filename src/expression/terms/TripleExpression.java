package expression.terms;

import expression.exceptions.IncorrectActionException;
import expression.exceptions.OverflowException;

/**
 * @author Ilya Kokorin
 */
public interface TripleExpression <T> {
    T evaluate(T x, T y, T z) throws OverflowException, IncorrectActionException;
}
