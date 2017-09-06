package kanekotic.specflow.rider;

import gherkin.Parser;
import gherkin.ast.GherkinDocument;
import gherkin.pickles.Compiler;

import java.util.ArrayList;
import java.util.List;

public class SpecflowAnalizer implements ISpecflowAnalizer {

    public SpecflowAnalizer(Compiler compiler, Parser<GherkinDocument> parser) {
    }

    @Override
    public List<String> analize(String file) {

//        Parser<GherkinDocument> parser = new Parser<GherkinDocument>(new AstBuilder());
//        GherkinDocument gherkinDocument = parser.parse("Feature: ...");
//        List<Pickle> pickles = new Compiler().compile(gherkinDocument);

        return new ArrayList<String>();

    }

}
