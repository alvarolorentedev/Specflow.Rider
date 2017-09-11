package kanekotic.specflow.rider;

import gherkin.ast.Feature;

public interface ISpecflowTranslator {
    SpecflowFileContents translate(Feature content, SpecflowFileContents scenarionContent, ITestFrameworkConstants constants);
}

