package expression.exceptions;

/**
 * @author Ilya Kokorin
 */
public class NoClosingBracketException extends Exception {
    private int positionOpen, positionClose;
    private String expression;
    public NoClosingBracketException(String message, String expression, int positionOpen, int positionClose) {
        super(message + positionClose + "for open bracket at position" + positionOpen);
        this.positionOpen = positionOpen;
        this.positionClose = positionClose;
        this.expression = expression;
    }

    public String getMessage() {
        StringBuilder resBuilder = new StringBuilder(super.getMessage() + "\n" + this.expression + "\n");
        for (int i = 0; i < positionOpen; i++) {
            resBuilder.append(" ");
        }
        resBuilder.append("^");
        for (int i = positionOpen + 1; i < positionClose; i++) {
            resBuilder.append(" ");
        }
        resBuilder.append("^");
        return resBuilder.toString();
    }
}
