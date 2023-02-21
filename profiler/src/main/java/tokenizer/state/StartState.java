package tokenizer.state;

import token.CloseParenthesis;
import token.OpenParenthesis;
import token.Operation;
import token.Token;
import tokenizer.Tokenizer;

import java.text.ParseException;

public class StartState implements State {
    static boolean canProcess(int character) {
        return switch (character) {
            case '+', '-', '*', '/', '(', ')' -> true;
            default -> false;
        };
    }

    @Override
    public Token createToken(Tokenizer tokenizer) {
        int currChar = tokenizer.getCurrentChar();

        return switch (currChar) {
            case '(' -> new OpenParenthesis();
            case ')' -> new CloseParenthesis();
            case '+', '-', '*', '/' -> new Operation((char) currChar);
            default -> throw new IllegalStateException("Illegal input for start state: " + currChar);
        };
    }

    @Override
    public void setNextState(Tokenizer tokenizer) throws ParseException {
        tokenizer.readChar();
        int currChar = tokenizer.getCurrentChar();

        if (currChar == -1) {
            tokenizer.setState(new EndState());
        } else if (Character.isDigit(currChar)) {
            tokenizer.setState(new NumberState());
        } else if (!canProcess(currChar)) {
            tokenizer.setState(new ErrorState());
        }
    }
}
