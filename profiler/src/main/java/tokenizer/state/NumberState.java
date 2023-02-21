package tokenizer.state;

import token.NumberToken;
import token.Token;
import tokenizer.Tokenizer;

import java.text.ParseException;

public class NumberState implements State {
    static boolean canProcess(int character) {
        return Character.isDigit(character);
    }

    @Override
    public Token createToken(Tokenizer tokenizer) throws ParseException {
        StringBuilder builder = new StringBuilder();
        int currChar = tokenizer.getCurrentChar();

        while (Character.isDigit(currChar)) {
            builder.append((char) currChar);
            tokenizer.nextChar();
            currChar = tokenizer.getCurrentChar();
        }

        return new NumberToken(builder.toString());
    }

    @Override
    public void setNextState(Tokenizer tokenizer) throws ParseException {
        tokenizer.skipWhitespace();
        int currChar = tokenizer.getCurrentChar();

        if (StartState.canProcess(currChar)) {
            tokenizer.setState(new StartState());
        } else if (EndState.canProcess(currChar)) {
            tokenizer.setState(new EndState());
        } else if (!canProcess(currChar)) {
            tokenizer.setState(new ErrorState());
        }
    }
}
