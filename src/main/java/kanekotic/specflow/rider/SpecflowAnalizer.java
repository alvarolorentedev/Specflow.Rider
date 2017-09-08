package kanekotic.specflow.rider;

import gherkin.Parser;
import gherkin.ast.GherkinDocument;
import gherkin.pickles.Compiler;
import gherkin.pickles.Pickle;

import java.util.List;

public class SpecflowAnalizer implements ISpecflowAnalizer {

    private Compiler compiler;
    private Parser<GherkinDocument> parser;

    public SpecflowAnalizer(Compiler compiler, Parser<GherkinDocument> parser) {
        this.compiler = compiler;
        this.parser = parser;
    }

    @Override
    public SpecflowFileContents analize(String file) {
        GherkinDocument gherkinDocument = parser.parse(file);
        List<Pickle> pickles = compiler.compile(gherkinDocument);
        return new SpecflowFileContents();
    }

}
