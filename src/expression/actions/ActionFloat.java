package expression.actions;

import expression.exceptions.IncorrectActionException;

/**
 * @author Ilya Kokorin
 */
public class ActionFloat implements Action<Float> {

    final private static float eps = (float)1e-5;

    /*private void checkCorrect(Float a, Float b) throws IncorrectActionException {
        if (Math.abs(b) < eps) {
            throw new IncorrectActionException("division by zero");
        }
    }*/

    public Float parse(String a) throws NumberFormatException {
        return Float.parseFloat(a);
    }

    public Float add(Float a, Float b) {
        return a + b;
    }

    public Float subtract(Float a, Float b) {
        return a - b;
    }

    public Float multiply(Float a, Float b) {
        return a * b;
    }

    public Float divide(Float a, Float b) throws IncorrectActionException {
        return a / b;
    }

    public Float negate(Float a) {
        return -a;
    }

    public Float mod(Float a, Float b) throws IncorrectActionException {
        return a % b;
    }

    public Float abs(Float a) {
        if (a < 0) {
            return -a;
        } else {
            return a;
        }
    }

    public Float square(Float a) {
        return a * a;
    }
}
