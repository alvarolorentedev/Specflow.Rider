package kanekotic.specflow.rider;

import gherkin.ast.Feature;
import gherkin.ast.ScenarioDefinition;

import java.util.List;

public interface ISpecflowTranslator {
    SpecflowFileContents translate(Feature content, SpecflowFileContents scenarios, ITestFrameworkConstants constants);
    SpecflowFileContents translate(List<ScenarioDefinition> content, ITestFrameworkConstants constants);
}

