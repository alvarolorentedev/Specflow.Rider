package kanekotic.specflow.rider;

import gherkin.Parser;
import gherkin.ast.GherkinDocument;
import gherkin.pickles.Compiler;

public class SpecflowAnalizer implements ISpecflowAnalizer {

    public SpecflowAnalizer(Compiler compiler, Parser<GherkinDocument> parser) {
    }

    @Override
    public SpecflowFileContents analize(String file) {

        return new SpecflowFileContents();

    }

}
