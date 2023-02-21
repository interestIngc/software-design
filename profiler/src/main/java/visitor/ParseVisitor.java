package visitor;

import token.CloseParenthesis;
import token.NumberToken;
import token.OpenParenthesis;
import token.Operation;
import token.Token;
import visitor.expression.BinaryExpression;
import visitor.expression.PostfixExpression;
import visitor.expression.UnaryExpression;

import java.util.List;
import java.util.Stack;

public class ParseVisitor implements TokenVisitor {
    private final Stack<PostfixExpression> expressions = new Stack<>();
    private final Stack<Token> operations = new Stack<>();

    public List<Token> getTokens() {
        if (expressions.size() != operations.size() + 1) {
            throw new IllegalArgumentException(
                    "Expression invalid, the number of operands doesn't match the number of operators");
        }

        Stack<PostfixExpression> currExpressions = new Stack<>();
        Stack<Operation> currOperations = new Stack<>();
        while (!operations.empty()) {
            Token token = operations.pop();
            if (token instanceof OpenParenthesis) {
                throw new IllegalArgumentException("Close parenthesis expected");
            }
            currExpressions.add(expressions.pop());
            currOperations.add((Operation) token);
        }

        PostfixExpression expression = makeBinaryExpression(currExpressions, currOperations);

        return expression.toList();
    }

    @Override
    public void visit(OpenParenthesis token) {
        operations.add(token);
    }

    @Override
    public void visit(CloseParenthesis token) {
        Stack<PostfixExpression> currExpressions = new Stack<>();
        Stack<Operation> currOperations = new Stack<>();
        while (!operations.empty() && !(operations.peek() instanceof OpenParenthesis)) {
            currExpressions.add(getLastExpression());
            currOperations.add((Operation) operations.pop());
        }

        if (operations.empty()) {
            throw new IllegalArgumentException("Open parenthesis expected");
        }
        operations.pop();
        PostfixExpression expression = makeBinaryExpression(currExpressions, currOperations);

        addExpression(expression);
    }

    @Override
    public void visit(NumberToken token) {
        addExpression(new UnaryExpression(token));
    }

    @Override
    public void visit(Operation token) {
        operations.add(token);
    }

    private PostfixExpression makeBinaryExpression(
            Stack<PostfixExpression> currExpressions,
            Stack<Operation> currOperations) {
        PostfixExpression expression = getLastExpression();

        while (!currOperations.empty()) {
            expression = new BinaryExpression(expression, currExpressions.pop(), currOperations.pop());
        }

        return expression;
    }

    private void addExpression(PostfixExpression expression) {
        if (!operations.empty() && operations.peek() instanceof Operation) {
            Operation operation = (Operation) operations.peek();
            if (operation.getPriority() == 2) {
                operations.pop();
                PostfixExpression left = getLastExpression();
                expressions.add(new BinaryExpression(left, expression, operation));
                return;
            }
        }
        expressions.add(expression);
    }

    private PostfixExpression getLastExpression() {
        if (expressions.empty()) {
            throw new IllegalArgumentException(
                    "Expression is invalid, found binary operation with no left argument provided");
        }
        return expressions.pop();
    }
}
