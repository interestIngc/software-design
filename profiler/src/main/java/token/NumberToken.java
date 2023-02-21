package token;

import visitor.TokenVisitor;

public class NumberToken implements Token {
    private final int number;

    public NumberToken(String number) {
        this.number = Integer.parseInt(number);
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("NUMBER(%d)", number);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof NumberToken token) {
            return token.getNumber() == this.number;
        }
        return false;
    }
}
