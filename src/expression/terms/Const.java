package expression.terms;

/**
 * @author Ilya Kokorin
 */
public class Const<T> implements TripleExpression<T> {
    private T value;
    public Const(T value) {
        this.value = value;
    }

    public T evaluate(T x, T y, T z) {
        return value;
    }
}
