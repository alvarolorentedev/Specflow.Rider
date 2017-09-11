package kanekotic.specflow.rider;

import gherkin.Parser;
import gherkin.ast.GherkinDocument;

public class SpecflowAnalizer implements ISpecflowAnalizer {

    private Parser<GherkinDocument> parser;

    public SpecflowAnalizer(Parser<GherkinDocument> parser) {
        this.parser = parser;
    }

    @Override
    public SpecflowFileContents analize(String file) {
        GherkinDocument gherkinDocument = parser.parse(file);
        return new SpecflowFileContents();
    }

}
