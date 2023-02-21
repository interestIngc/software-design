import profiler.Profiler;
import token.Token;
import tokenizer.Tokenizer;
import visitor.ParseVisitor;

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

        System.setProperty("package", args[0]);

        String expr = "(23 + 10) * 5";
        InputStream inputStream = new ByteArrayInputStream(expr.getBytes(StandardCharsets.UTF_8));
        Tokenizer tokenizer = new Tokenizer(inputStream);
        ParseVisitor parseVisitor = new ParseVisitor();

        List<Token> tokens = tokenizer.tokenize();
        for (Token token : tokens) {
            token.accept(parseVisitor);
        }

        Profiler.printResults();
    }
}
