package kanekotic.specflow.rider;

import gherkin.ast.Feature;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.Tag;

import java.util.List;

public interface ISpecflowTranslator {
    SpecflowFileContents translate(Feature content, SpecflowFileContents scenarios, ITestFrameworkConstants constants);
    SpecflowFileContents translate(List<ScenarioDefinition> scenarios, List<Tag> tags, ITestFrameworkConstants constants);
}

