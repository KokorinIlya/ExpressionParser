package expression.run;

import expression.actions.Action;
import expression.actions.ActionBigInteger;
import expression.actions.ActionByte;
import expression.actions.ActionDouble;
import expression.actions.ActionFloat;
import expression.actions.ActionInteger;
import expression.exceptions.WrongModeException;

/**
 * @author Ilya Kokorin
 */

import java.util.HashMap;

public class ModeGetter {
    private final static HashMap<String, Action<?>> MODES = new HashMap<>();

    static {
        MODES.put("i", new ActionInteger(true));
        MODES.put("u", new ActionInteger(false));
        MODES.put("bi", new ActionBigInteger());
        MODES.put("b", new ActionByte());
        MODES.put("d", new ActionDouble());
        MODES.put("f", new ActionFloat());
    }

    public static Action<?> getAction(String mode) throws WrongModeException {
        Action<?> action = MODES.get(mode);
        if (action == null) {
            throw new WrongModeException("Used unknown mode " + mode + ", try using one of this modes: " + MODES.keySet().toString());
        }
        return action;
    }
}
