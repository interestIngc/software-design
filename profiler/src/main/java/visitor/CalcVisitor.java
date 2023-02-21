package visitor;

import token.CloseParenthesis;
import token.NumberToken;
import token.OpenParenthesis;
import token.Operation;

import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    Stack<Double> stack = new Stack<>();

    public double getValue() {
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Expression invalid");
        }
        return stack.pop();
    }

    @Override
    public void visit(OpenParenthesis token) {
        throw new RuntimeException("Expression in postfix form expected");
    }

    @Override
    public void visit(CloseParenthesis token) {
        throw new RuntimeException("Expression in postfix form expected");
    }

    @Override
    public void visit(NumberToken token) {
        stack.add((double) token.getNumber());
    }

    @Override
    public void visit(Operation token) {
        if (stack.size() < 2) {
            throw new IllegalArgumentException(
                    "Binary operation " + token.toString() + " has invalid number of arguments");
        }
        double right = stack.pop();
        double left = stack.pop();
        double result = 0;

        switch (token.getOperation()) {
            case '+' -> result = left + right;
            case '-' -> result = left - right;
            case '*' -> result = left * right;
            case '/' -> {
                if (right == 0) {
                    throw new IllegalArgumentException("Divisor must not be 0");
                }
                result = left / right;
            }
        }

        stack.add(result);
    }
}
