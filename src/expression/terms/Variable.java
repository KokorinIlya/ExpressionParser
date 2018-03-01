package expression.terms;

/**
 * @author Ilya Kokorin
 */
public class Variable<T> implements TripleExpression<T> {
    private String name;
    public Variable(String name) {
        this.name = name;
    }

    public T evaluate(T x, T y, T z) {
        if (name.equals("x")) {
            return x;
        }
        if (name.equals("y")) {
            return y;
        }
        return z;
    }

}
