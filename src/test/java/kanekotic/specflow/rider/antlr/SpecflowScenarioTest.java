package kanekotic.specflow.rider.antlr;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SpecflowScenarioTest {

    @Test
    public void analize() throws Exception {
        SpecflowScenarioParser parser = createParser("Scenario: ");;
        List<String> result = parser.scenario().values;
        assert result.isEmpty();
    }


    private SpecflowScenarioParser createParser(String snippet) throws IOException {
        InputStream stream = new ByteArrayInputStream(snippet.getBytes(StandardCharsets.UTF_8));
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(stream);
        SpecflowScenarioLexer lexer = new SpecflowScenarioLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        SpecflowScenarioParser parser = new SpecflowScenarioParser(tokenStream);
        return parser;

    }
}
