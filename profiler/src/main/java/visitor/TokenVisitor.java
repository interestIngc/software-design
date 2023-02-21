package visitor;

import token.CloseParenthesis;
import token.NumberToken;
import token.OpenParenthesis;
import token.Operation;

public interface TokenVisitor {
    void visit(OpenParenthesis token);

    void visit(CloseParenthesis token);

    void visit(NumberToken token);

    void visit(Operation token);
}
