package expression.actions;

import expression.exceptions.IncorrectActionException;

/**
 * @author Ilya Kokorin
 */
public class ActionByte implements Action<Byte> {

    private final static int byteConstructor = 0xFF;

    private Byte makeByte(int x) {
        Integer res = x & byteConstructor;
        return res.byteValue();
    }

    private void checkCorrect(Byte a, Byte b) throws IncorrectActionException{
        if (b == 0) {
            throw new IncorrectActionException("division by zero");
        }
    }

    public Byte parse(String a) throws NumberFormatException {
        return Byte.parseByte(a);
    }

    public Byte add(Byte a, Byte b) {
        return makeByte(a + b);
    }
    public Byte subtract(Byte a, Byte b) {
        return makeByte(a - b);
    }
    public Byte multiply(Byte a, Byte b) {
        return makeByte(a * b);
    }
    public Byte divide(Byte a, Byte b) throws IncorrectActionException {
        checkCorrect(a,b);
        return makeByte(a / b);
    }
    public Byte mod(Byte a, Byte b) throws IncorrectActionException {
        checkCorrect(a, b);
        return makeByte(a % b);
    }
    public Byte negate(Byte a) {
        return makeByte(-a);
    }
    public Byte abs(Byte a) {
        if (a < 0) {
            Byte temp = 0;
            return makeByte(subtract(temp, a));
        } else {
            return makeByte(a);
        }
    }
    public Byte square(Byte a) {
        return makeByte(multiply(a, a));
    }
}
