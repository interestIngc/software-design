package tokenizer.state;

import token.Token;
import tokenizer.Tokenizer;

public class ErrorState implements State {
    @Override
    public Token createToken(Tokenizer tokenizer) {
        throw new IllegalArgumentException("Expression illegal");
    }

    @Override
    public void setNextState(Tokenizer tokenizer) {
    }
}
