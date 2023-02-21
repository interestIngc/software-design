package tokenizer.state;

import token.Token;
import tokenizer.Tokenizer;

public class EndState implements State {
    static boolean canProcess(int character) {
        return character == -1;
    }

    @Override
    public Token createToken(Tokenizer tokenizer) {
        throw new IllegalStateException("End state cannot create a token");
    }

    @Override
    public void setNextState(Tokenizer tokenizer) {
        throw new IllegalStateException("End state doesn't have a next state");
    }
}
