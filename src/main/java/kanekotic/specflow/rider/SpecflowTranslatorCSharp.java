package kanekotic.specflow.rider;

import gherkin.ast.Feature;
import gherkin.ast.ScenarioDefinition;

import java.util.List;

public class SpecflowTranslatorCSharp implements ISpecflowTranslator {

    public static final String BodyContent = String.join(
            System.getProperty("line.separator"),
            "[System.CodeDom.Compiler.GeneratedCodeAttribute(\"TechTalk.SpecFlow\", \"1.3.0.0\")]",
            "[System.Runtime.CompilerServices.CompilerGeneratedAttribute()]",
            "%1$s",
            "public partial class %2$sFeature",
            "{",
            "private static TechTalk.SpecFlow.ITestRunner testRunner;",
            "[NUnit.Framework.TestFixtureSetUpAttribute()]",
            "%3$s",
            "{",
            "testRunner = TechTalk.SpecFlow.TestRunnerManager.GetTestRunner();",
            "TechTalk.SpecFlow.FeatureInfo featureInfo = new TechTalk.SpecFlow.FeatureInfo(new System.Globalization.CultureInfo(\"en-US\"), \"%4$s\", \"%5$s\", ((string[])(null)));",
            "testRunner.OnFeatureStart(featureInfo);",
            "}",
            "%6$s",
            "{",
            "testRunner.OnFeatureEnd();",
            "testRunner = null;",
            "}",
            "%7$s",
            "{",
            "testRunner.OnScenarioStart(scenarioInfo);",
            "}",
            "%8$s",
            "{",
            "testRunner.OnScenarioEnd();",
            "}",
            "%9$s",
            "}");

    @Override
    public SpecflowFileContents translate(Feature content, SpecflowFileContents scenarios, ITestFrameworkConstants constants) {
        String resultContent = String.format(BodyContent,
                constants.getExpectedClassAttributes(content.getName()),
                content.getName().replaceAll("[^A-Za-z0-9]", ""),
                constants.getTestFixtureSetupHeader(),
                content.getName(),
                content.getDescription(),
                constants.getTestFixtureTearDownHeader(),
                constants.getTestScenarioSetupHeader(),
                constants.getTestScenarioTearDownHeader(),
                scenarios.feature);
        return new SpecflowFileContents(resultContent, "");
    }

    @Override
    public SpecflowFileContents translate(List<ScenarioDefinition> content, ITestFrameworkConstants constants) {
        return new SpecflowFileContents();
    }
}
