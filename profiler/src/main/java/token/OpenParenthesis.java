package token;

import visitor.TokenVisitor;

public class OpenParenthesis implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "LEFT";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof OpenParenthesis;
    }
}
