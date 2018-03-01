package expression.parser;

import expression.exceptions.ParsingException;
import expression.terms.TripleExpression;

/**
 * @author Ilya Kokorin
 */
public interface Parser<T> {
    TripleExpression<T> parse(String expression) throws ParsingException;
}
