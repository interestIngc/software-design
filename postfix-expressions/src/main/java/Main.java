import token.Token;
import tokenizer.Tokenizer;
import visitor.CalcVisitor;
import visitor.ParseVisitor;
import visitor.PrintVisitor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        if (args.length != 1) {
            System.err.println("One argument should be provided, got " + args.length);
            return;
        }

        InputStream inputStream = new ByteArrayInputStream(args[0].getBytes(StandardCharsets.UTF_8));
        Tokenizer tokenizer = new Tokenizer(inputStream);
        ParseVisitor parseVisitor = new ParseVisitor();
        PrintVisitor printVisitor = new PrintVisitor();
        CalcVisitor calcVisitor = new CalcVisitor();

        List<Token> tokens = tokenizer.tokenize();
        for (Token token : tokens) {
            token.accept(parseVisitor);
        }

        List<Token> postfixForm = parseVisitor.getTokens();
        for (Token token : postfixForm) {
            token.accept(printVisitor);
            token.accept(calcVisitor);
        }

        System.out.println();
        System.out.println(calcVisitor.getValue());
    }
}
