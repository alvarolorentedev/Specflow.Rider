package kanekotic.specflow.rider;

import gherkin.Parser;
import gherkin.ast.Feature;
import gherkin.ast.GherkinDocument;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.Tag;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SpecflowAnalizerTest {

    @Test
    public void analizeCallsCorrectProcess() throws Exception {
        Parser<GherkinDocument> parser = (Parser<GherkinDocument>) mock(Parser.class);
        ISpecflowTranslator translator = mock(ISpecflowTranslator.class);
        ITestFrameworkConstants constants = mock(ITestFrameworkConstants.class);

        String document = Faker.getRandomString();
        GherkinDocument gherkinDocument = mock(GherkinDocument.class);
        when(parser.parse(document)).thenReturn(gherkinDocument);
        Feature feature = mock(Feature.class);
        List<ScenarioDefinition> scenarios = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        when(feature.getChildren()).thenReturn(scenarios);
        when(feature.getTags()).thenReturn(tags);

        when(gherkinDocument.getFeature()).thenReturn(feature);
        SpecflowFileContents translatedScenarios =new SpecflowFileContents(Faker.getRandomString(), Faker.getRandomString());
        SpecflowFileContents translatedFeature =new SpecflowFileContents(Faker.getRandomString(), Faker.getRandomString());
        SpecflowFileContents translatedFile =new SpecflowFileContents(Faker.getRandomString(), Faker.getRandomString());
        String namespace = Faker.getRandomString();
        when(translator.translate(scenarios, tags, constants)).thenReturn(translatedScenarios);
        when(translator.translate(feature, translatedScenarios,constants)).thenReturn(translatedFeature);
        when(translator.translate(namespace,translatedFeature, constants)).thenReturn(translatedFile);

        SpecflowAnalyzer analyzer = new SpecflowAnalyzer(parser, translator, constants);
        SpecflowFileContents contents = analyzer.analyze(document, namespace);

        verify(parser).parse(document);
        assertEquals(translatedFile.feature, contents.feature);
        assertEquals(translatedFile.steps, contents.steps);
    }

}