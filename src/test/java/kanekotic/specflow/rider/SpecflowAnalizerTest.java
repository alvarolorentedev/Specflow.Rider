package kanekotic.specflow.rider;

import gherkin.Parser;
import gherkin.ast.GherkinDocument;
import gherkin.pickles.Compiler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SpecflowAnalizerTest {

    @Test
    public void analize() throws Exception {
        Parser<GherkinDocument> parser = (Parser<GherkinDocument>) mock(Parser.class);
        Compiler compiler = mock(Compiler.class);
        SpecflowAnalizer analizer = new SpecflowAnalizer(compiler, parser);
        SpecflowFileContents contents = analizer.analize("");
        assertEquals("", contents.feature);
        assertEquals("", contents.steps);
    }

}