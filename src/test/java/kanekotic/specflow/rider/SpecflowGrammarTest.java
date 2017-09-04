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
        SpecflowFeatureParser parser = createParser("Feature: ");;
        List<String> result = parser.file().values;
        assert result.isEmpty();
    }

    private SpecflowFeatureParser createParser(String snippet) throws IOException {
        InputStream stream = new ByteArrayInputStream(snippet.getBytes(StandardCharsets.UTF_8));
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(stream);
        SpecflowFeatureLexer lexer = new SpecflowFeatureLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        SpecflowFeatureParser parser = new SpecflowFeatureParser(tokenStream);
        return parser;

    }

}
