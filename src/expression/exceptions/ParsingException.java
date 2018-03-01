package expression.exceptions;

/**
 * @author Ilya Kokorin
 */
public class ParsingException extends Exception {
    private int position;
    private String expression;
    public ParsingException(String message, String expression, int position) {
        super(message + position);
        this.position = position;
        this.expression = expression;
    }

    public String getMessage() {
        StringBuilder resBuilder = new StringBuilder(super.getMessage() + "\n" + this.expression + "\n");
        for (int i = 0; i < position; i++) {
            resBuilder.append(" ");
        }
        resBuilder.append("^");
        return resBuilder.toString();
    }
}
