package expression.actions;

import expression.exceptions.IncorrectActionException;

/**
 * @author Ilya Kokorin
 */
public class ActionDouble implements Action<Double> {

    final private static double eps = 1e-10;

    /*private void checkCorrect(double a, double b) throws IncorrectActionException {
        if (Math.abs(b) < eps) {
            throw new IncorrectActionException("division by zero");
        }
    }*/

    public Double parse(String a) throws NumberFormatException {
        return Double.parseDouble(a);
    }

    public Double add(Double a, Double b) {
        return a + b;
    }

    public Double subtract(Double a, Double b) {
        return a - b;
    }

    public Double multiply(Double a, Double b) {
        return a * b;
    }

    public Double divide(Double a, Double b) throws IncorrectActionException {
        return a / b;
    }

    public Double negate(Double a) {
        return -a;
    }

    public Double mod(Double a, Double b) throws IncorrectActionException {
        return a % b;
    }

    public Double abs(Double a) {
        if (a < 0) {
            return -a;
        } else {
            return a;
        }
    }

    public Double square(Double a) {
        return a * a;
    }

}
