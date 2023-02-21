package token;

import visitor.TokenVisitor;

public class CloseParenthesis implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "RIGHT";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof CloseParenthesis;
    }
}
