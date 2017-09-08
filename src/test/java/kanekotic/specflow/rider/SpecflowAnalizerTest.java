package kanekotic.specflow.rider;

import gherkin.Parser;
import gherkin.ast.GherkinDocument;
import gherkin.pickles.Compiler;
import gherkin.pickles.Pickle;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SpecflowAnalizerTest {

    @Test
    public void analizeCallsCorrectProcess() throws Exception {
        Parser<GherkinDocument> parser = (Parser<GherkinDocument>) mock(Parser.class);
        Compiler compiler = mock(Compiler.class);
        SpecflowAnalizer analizer = new SpecflowAnalizer(compiler, parser);
        String document = "my string";
        GherkinDocument gherkinDocument = mock(GherkinDocument.class);
        List<Pickle> pickles = (List<Pickle>) mock(List.class);
        when(parser.parse(document)).thenReturn(gherkinDocument);
        when(compiler.compile(gherkinDocument)).thenReturn(pickles);

        SpecflowFileContents contents = analizer.analize(document);

        verify(parser).parse(document);
        verify(compiler).compile(gherkinDocument);
        assertEquals("", contents.feature);
        assertEquals("", contents.steps);
    }

}