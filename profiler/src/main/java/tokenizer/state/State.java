package tokenizer.state;

import token.Token;
import tokenizer.Tokenizer;

import java.text.ParseException;

public interface State {
    Token createToken(Tokenizer tokenizer) throws ParseException;

    void setNextState(Tokenizer tokenizer) throws ParseException;
}
