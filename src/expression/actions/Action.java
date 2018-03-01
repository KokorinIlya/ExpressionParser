package expression.actions;

import expression.exceptions.IncorrectActionException;
import expression.exceptions.OverflowException;

/**
 @author Ilya Kokorin
 */
public interface Action<T> {
    T parse(String a) throws NumberFormatException;
    T add(T a, T b) throws OverflowException;
    T subtract(T a, T b) throws OverflowException;
    T multiply(T a, T b) throws OverflowException;
    T divide(T a, T b) throws OverflowException, IncorrectActionException;
    T mod(T a, T b) throws IncorrectActionException;
    T negate(T a) throws OverflowException;
    T abs(T a) throws OverflowException;
    T square(T a) throws OverflowException;
}
