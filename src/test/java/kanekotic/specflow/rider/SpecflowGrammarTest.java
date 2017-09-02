package kanekotic.specflow.rider;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SpecflowGrammarTest {

    @Test
    public void analize() throws Exception {
        SpecflowParser parser = createParser("\n");;
        List<String> result = parser.file().values;
        assert result.isEmpty();
    }

    private SpecflowParser createParser(String snippet) throws IOException {
        InputStream stream = new ByteArrayInputStream(snippet.getBytes(StandardCharsets.UTF_8));
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(stream);
        SpecflowLexer lexer = new SpecflowLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        SpecflowParser parser = new SpecflowParser(tokenStream);
        return parser;

    }

}
