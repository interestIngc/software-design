package visitor.expression;

import token.NumberToken;
import token.Token;

import java.util.List;

public class UnaryExpression implements PostfixExpression {
    private final NumberToken numberToken;

    public UnaryExpression(NumberToken numberToken) {
        this.numberToken = numberToken;
    }

    @Override
    public List<Token> toList() {
        return List.of(numberToken);
    }
}
