package visitor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.util.List;

public class CalcVisitorTest {
    @Test
    public void getValue_simpleExpression() {
        List<Token> postfixExpression = List.of(
                new NumberToken("23"),
                new NumberToken("10"),
                new Operation('+'),
                new NumberToken("5"),
                new Operation('*')
        );

        CalcVisitor calcVisitor = new CalcVisitor();
        for (Token token : postfixExpression) {
            token.accept(calcVisitor);
        }
        double value = calcVisitor.getValue();

        assertThat(value).isEqualTo(165);
    }

    @Test
    public void getValue_longExpression() {
        List<Token> postfixExpression = List.of(
                new NumberToken("23"),
                new NumberToken("10"),
                new Operation('+'),
                new NumberToken("5"),
                new Operation('*'),
                new NumberToken("3"),
                new NumberToken("32"),
                new NumberToken("5"),
                new Operation('+'),
                new Operation('*'),
                new NumberToken("10"),
                new NumberToken("4"),
                new NumberToken("5"),
                new Operation('*'),
                new Operation('-'),
                new Operation('*'),
                new Operation('-'),
                new NumberToken("8"),
                new NumberToken("2"),
                new Operation('/'),
                new Operation('+')
        );

        CalcVisitor calcVisitor = new CalcVisitor();
        for (Token token : postfixExpression) {
            token.accept(calcVisitor);
        }
        double value = calcVisitor.getValue();

        assertThat(value).isEqualTo(1279);
    }
}
