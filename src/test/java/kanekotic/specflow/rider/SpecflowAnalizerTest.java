package kanekotic.specflow.rider;

import gherkin.Parser;
import gherkin.ast.GherkinDocument;
import org.junit.Test;

import gherkin.pickles.Compiler;

import java.util.List;

import static org.mockito.Mockito.mock;

public class SpecflowAnalizerTest {

    @Test
    public void analize() throws Exception {
        Parser<GherkinDocument> parser = (Parser<GherkinDocument>) mock(Parser.class);
        Compiler compiler = mock(Compiler.class);
        SpecflowAnalizer analizer = new SpecflowAnalizer(compiler, parser);
        List<String> newFiles = analizer.analize("");
        assert newFiles.isEmpty();
    }

}