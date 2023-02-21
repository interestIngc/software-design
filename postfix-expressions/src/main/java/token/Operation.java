package token;

import visitor.TokenVisitor;

public class Operation implements Token {
    private final char operation;
    private final int priority;

    public Operation(char operation) {
        this.operation = operation;
        this.priority = switch (operation) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> 0;
        };
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public char getOperation() {
        return operation;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return switch (operation) {
            case '+' -> "PLUS";
            case '-' -> "MINUS";
            case '*' -> "MUL";
            case '/' -> "DIV";
            default -> "";
        };
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Operation operation) {
            return operation.getOperation() == this.operation;
        }
        return false;
    }
}
