package kanekotic.specflow.rider;

import gherkin.Parser;
import gherkin.ast.GherkinDocument;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SpecflowAnalizerTest {

    @Test
    public void analizeCallsCorrectProcess() throws Exception {
        Parser<GherkinDocument> parser = (Parser<GherkinDocument>) mock(Parser.class);
        SpecflowAnalizer analizer = new SpecflowAnalizer(parser);
        String document = "my string";
        GherkinDocument gherkinDocument = mock(GherkinDocument.class);
        when(parser.parse(document)).thenReturn(gherkinDocument);
        SpecflowFileContents contents = analizer.analize(document);

        verify(parser).parse(document);
        assertEquals("", contents.feature);
        assertEquals("", contents.steps);
    }

}