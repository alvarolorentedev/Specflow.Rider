package kanekotic.specflow.rider;

import com.google.inject.Inject;
import gherkin.Parser;
import gherkin.ast.Feature;
import gherkin.ast.GherkinDocument;

public class SpecflowAnalyzer implements ISpecflowAnalyzer {

    private final Parser<GherkinDocument> parser;
    private final ISpecflowTranslator translator;
    private final ITestFrameworkConstants constants;

    @Inject
    public SpecflowAnalyzer(Parser<GherkinDocument> parser, ISpecflowTranslator translator, ITestFrameworkConstants constants) {
        this.parser = parser;
        this.translator = translator;
        this.constants = constants;
    }

    @Override
    public SpecflowFileContents analyze(String file, String namespace) {
        Feature feature = parser.parse(file).getFeature();
        SpecflowFileContents scenariosTranslate = translator.translate(feature.getChildren(), feature.getTags(), constants);
        SpecflowFileContents featureTranslate = translator.translate(feature,scenariosTranslate, constants);
        SpecflowFileContents FileTranslate = translator.translate(namespace,featureTranslate, constants);
        return FileTranslate;
    }

}
