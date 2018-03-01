package expression.exceptions;

/**
 * @author Ilya Kokorin
 */
public class OverflowException extends Exception {
    public OverflowException() {
        super("overflow");
    }
}
