package expression.actions;

import expression.exceptions.IncorrectActionException;
import expression.exceptions.OverflowException;

/**
 * @author Ilya Kokorin
 */
public class ActionInteger implements Action<Integer> {
    private boolean checked;

    public ActionInteger(boolean checked) {
        this.checked = checked;
    }


    private void checkAdd(int first, int second) throws OverflowException {
        if (first > 0 && second > Integer.MAX_VALUE - first) {
            throw new OverflowException();
        }
        if (first < 0 && second < Integer.MIN_VALUE - first){
            throw new OverflowException();
        }
    }

    private void checkAbs(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE){
            throw new OverflowException();
        }
    }

    private void checkDivide(int first, int second) throws IncorrectActionException, OverflowException {
        if (second == -1 && first == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    private void checkMultiply(int first, int second) throws OverflowException {
        if (first < 0 && second < 0 && first < Integer.MAX_VALUE / second){
            //System.out.println(1);
            throw new OverflowException();
        }
        if (first < 0 && second > 0 && first < Integer.MIN_VALUE / second){
            //System.out.println(2);
            throw new OverflowException();
        }
        if (first > 0 && second < 0 && second < Integer.MIN_VALUE / first){
            //System.out.println(3);
            throw new OverflowException();
        }
        if (first > 0 && second > 0 && first > Integer.MAX_VALUE / second){
            //System.out.println(4);
            throw new OverflowException();
        }
    }

    private void checkNegate(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE){
            throw new OverflowException();
        }
    }

    private void checkSubtract(int first, int second) throws OverflowException {
        if(second > 0 && first < Integer.MIN_VALUE + second) {
            throw new OverflowException();
        }
        if(second < 0 && first > Integer.MAX_VALUE + second) {
            throw new OverflowException();
        }
    }

    private void checkZero(int first, int second) throws IncorrectActionException {
        if (second == 0) {
            throw new IncorrectActionException("division by sero");
        }
    }

    public Integer parse(String a) throws NumberFormatException {
        return Integer.parseInt(a);
    }

    public Integer add(Integer a, Integer b) throws OverflowException {
        if (checked) {
            checkAdd(a, b);
        }
        return a + b;
    }
    public Integer subtract(Integer a, Integer b) throws OverflowException {
        if (checked) {
            checkSubtract(a, b);
        }
        return a - b;
    }
    public Integer multiply(Integer a, Integer b) throws OverflowException {
        if (checked) {
            checkMultiply(a, b);
        }
        return a * b;
    }
    public Integer divide(Integer a, Integer b) throws OverflowException, IncorrectActionException {
        if (checked) {
            checkDivide(a, b);
        }
        checkZero(a, b);
        return a / b;
    }
    public Integer mod(Integer a, Integer b) throws IncorrectActionException {
        checkZero(a, b);
        return a % b;
    }
    public Integer negate(Integer a) throws OverflowException {
        checkNegate(a);
        return -a;
    }
    public Integer abs(Integer a) throws OverflowException {
        if (a < 0) {
            return subtract(0, a);
        } else {
            return a;
        }
    }
    public Integer square(Integer a) throws OverflowException {
        return multiply(a, a);
    }

}
