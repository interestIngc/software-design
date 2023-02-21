package tokenizer;

import token.Token;
import tokenizer.state.EndState;
import tokenizer.state.StartState;
import tokenizer.state.State;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final InputStream inputStream;
    private int currChar;
    private int currPos;
    private State state;

    public Tokenizer(InputStream inputStream) {
        this.inputStream = inputStream;
        state = new StartState();
    }

    public void setState(State state) {
        this.state = state;
    }

    public void nextChar() throws ParseException {
        currPos++;

        try {
            currChar = inputStream.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), currPos);
        }
    }

    public void skipWhitespace() throws ParseException {
        while (Character.isWhitespace(currChar)) {
            nextChar();
        }
    }

    public void readChar() throws ParseException {
        nextChar();
        skipWhitespace();
    }

    public int getCurrentChar() {
        return currChar;
    }

    public List<Token> tokenize() throws ParseException {
        List<Token> tokens = new ArrayList<>();

        while (true) {
            state.setNextState(this);
            if (state instanceof EndState) {
                break;
            }
            tokens.add(state.createToken(this));
        }

        return tokens;
    }
}
