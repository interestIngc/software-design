package visitor.expression;

import token.Operation;
import token.Token;

import java.util.ArrayList;
import java.util.List;

public class BinaryExpression implements PostfixExpression {
    private final PostfixExpression left;
    private final PostfixExpression right;
    private final Operation operation;

    public BinaryExpression(PostfixExpression left, PostfixExpression right, Operation operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public List<Token> toList() {
        List<Token> result = new ArrayList<>();
        result.addAll(left.toList());
        result.addAll(right.toList());
        result.add(operation);
        return result;
    }
}
