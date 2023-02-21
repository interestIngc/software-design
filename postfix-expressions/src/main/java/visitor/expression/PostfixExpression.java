package visitor.expression;

import token.Token;

import java.util.List;

public interface PostfixExpression {
    List<Token> toList();
}
