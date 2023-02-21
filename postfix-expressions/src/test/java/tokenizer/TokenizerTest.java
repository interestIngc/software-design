package tokenizer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import token.CloseParenthesis;
import token.NumberToken;
import token.OpenParenthesis;
import token.Operation;
import token.Token;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;

public class TokenizerTest {

    @Test
    public void tokenize_simpleExpression() throws ParseException {
        String expression = "(23 + 10) * 5";
        InputStream inputStream =
                new ByteArrayInputStream(expression.getBytes(StandardCharsets.UTF_8));

        Tokenizer tokenizer = new Tokenizer(inputStream);
        List<Token> tokens = tokenizer.tokenize();

        List<Token> expected = List.of(
                new OpenParenthesis(),
                new NumberToken("23"),
                new Operation('+'),
                new NumberToken("10"),
                new CloseParenthesis(),
                new Operation('*'),
                new NumberToken("5")
        );

        assertThat(tokens).containsExactlyElementsOf(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tokenize_invalidExpression() throws ParseException {
        String expression = "23 + a";
        InputStream inputStream =
                new ByteArrayInputStream(expression.getBytes(StandardCharsets.UTF_8));

        Tokenizer tokenizer = new Tokenizer(inputStream);
        tokenizer.tokenize();
    }

    @Test
    public void tokenize_longExpression() throws ParseException {
        String expression = "(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2";
        InputStream inputStream =
                new ByteArrayInputStream(expression.getBytes(StandardCharsets.UTF_8));

        Tokenizer tokenizer = new Tokenizer(inputStream);
        List<Token> tokens = tokenizer.tokenize();

        List<Token> expected = List.of(
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

        assertThat(tokens).containsExactlyElementsOf(expected);
    }
}
