package expression.terms;


import expression.actions.Action;
import expression.exceptions.IncorrectActionException;
import expression.exceptions.OverflowException;

/**
 * @author Ilya Kokorin
 */
public abstract class AbstractBinaryOperator<T> implements TripleExpression<T> {
    private TripleExpression<T> first, second;

    protected Action<T> action;

    public AbstractBinaryOperator(TripleExpression<T> first, TripleExpression<T> second, Action<T> action){
        this.first = first;
        this.second = second;
        this.action = action;
    }

    abstract protected T operation(T firstOperand, T secondOperand) throws OverflowException, IncorrectActionException;

    public T evaluate(T x, T y, T z) throws OverflowException, IncorrectActionException{
        return operation(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
