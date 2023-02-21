package visitor;

import token.CloseParenthesis;
import token.NumberToken;
import token.OpenParenthesis;
import token.Operation;

public class PrintVisitor implements TokenVisitor {
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
        System.out.print(token.toString() + " ");
    }

    @Override
    public void visit(Operation token) {
        System.out.print(token.toString() + " ");
    }
}
