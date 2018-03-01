package expression.actions;

import expression.exceptions.IncorrectActionException;

import java.math.BigInteger;

/**
 *@author Ilya Kokorin
 */
public class ActionBigInteger implements Action<BigInteger> {

    final private BigInteger zero = BigInteger.ZERO;

    private void checkCorrect(BigInteger a, BigInteger b) throws IncorrectActionException {
        if (b.equals(zero)) {
            throw new IncorrectActionException("division by zero");
        }
    }

    public BigInteger parse(String a) throws NumberFormatException{
        return new BigInteger(a);
    }

    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }
    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }
    public BigInteger multiply(BigInteger a, BigInteger b)  {
        return a.multiply(b);
    }
    public BigInteger divide(BigInteger a, BigInteger b) throws IncorrectActionException {
        checkCorrect(a, b);
        return a.divide(b);
    }
    public BigInteger negate(BigInteger a) {
        return a.negate();
    }
    public BigInteger mod(BigInteger a, BigInteger b) throws IncorrectActionException {
        checkCorrect(a, b);
        return a.remainder(b);
    }
    public BigInteger square(BigInteger a) {
        return a.multiply(a);
    }

    public BigInteger abs(BigInteger a) {
        return a.abs();
    }
}
