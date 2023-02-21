package visitor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import token.CloseParenthesis;
import token.NumberToken;
import token.OpenParenthesis;
import token.Operation;
import token.Token;

import java.util.List;

public class ParseVisitorTest {
    @Test
    public void getTokens_simpleExpression() {
        List<Token> infixExpression = List.of(
                new OpenParenthesis(),
                new NumberToken("23"),
                new Operation('+'),
                new NumberToken("10"),
                new CloseParenthesis(),
                new Operation('*'),
                new NumberToken("5")
        );

        ParseVisitor parseVisitor = new ParseVisitor();
        for (Token token : infixExpression) {
            token.accept(parseVisitor);
        }
        List<Token> postfixExpression = parseVisitor.getTokens();

        List<Token> expected = List.of(
                new NumberToken("23"),
                new NumberToken("10"),
                new Operation('+'),
                new NumberToken("5"),
                new Operation('*')
        );

        assertThat(postfixExpression).containsExactlyElementsOf(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void visit_invalidExpression() {
        List<Token> infixExpression = List.of(
                new OpenParenthesis(),
                new NumberToken("23"),
                new Operation('+'),
                new NumberToken("10"),
                new Operation('*'),
                new NumberToken("5")
        );

        ParseVisitor parseVisitor = new ParseVisitor();
        for (Token token : infixExpression) {
            token.accept(parseVisitor);
        }
        parseVisitor.getTokens();
    }

    @Test
    public void getTokens_longExpression() {
        List<Token> infixExpression = List.of(
                new OpenParenthesis(),
                new NumberToken("23"),
                new Operation('+'),
                new NumberToken("10"),
                new CloseParenthesis(),
                new Operation('*'),
                new NumberToken("5"),
                new Operation('-'),
                new NumberToken("3"),
                new Operation('*'),
                new OpenParenthesis(),
                new NumberToken("32"),
                new Operation('+'),
                new NumberToken("5"),
                new CloseParenthesis(),
                new Operation('*'),
                new OpenParenthesis(),
                new NumberToken("10"),
                new Operation('-'),
                new NumberToken("4"),
                new Operation('*'),
                new NumberToken("5"),
                new CloseParenthesis(),
                new Operation('+'),
                new NumberToken("8"),
                new Operation('/'),
                new NumberToken("2")
        );

        ParseVisitor parseVisitor = new ParseVisitor();
        for (Token token : infixExpression) {
            token.accept(parseVisitor);
        }
        List<Token> postfixExpression = parseVisitor.getTokens();

        List<Token> expected = List.of(
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

        assertThat(postfixExpression).containsExactlyElementsOf(expected);
    }
}
