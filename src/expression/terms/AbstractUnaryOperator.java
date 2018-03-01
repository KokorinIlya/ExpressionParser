package expression.terms;


import expression.actions.Action;
import expression.exceptions.IncorrectActionException;
import expression.exceptions.OverflowException;

/**
 * @author Ilya Kokorin
 */
public abstract class AbstractUnaryOperator<T> implements TripleExpression<T>{
    private TripleExpression<T> expression;

    protected Action<T> action;

    public AbstractUnaryOperator(TripleExpression<T> expression, Action<T> action){
        this.expression = expression;
        this.action = action;
    }

    abstract protected T operation(T operand) throws OverflowException, IncorrectActionException;

    public T evaluate(T x, T y, T z) throws OverflowException, IncorrectActionException{
        return operation(expression.evaluate(x, y, z));
    }
}
