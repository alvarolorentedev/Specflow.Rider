package kanekotic.specflow.rider;

import gherkin.ast.Feature;
import gherkin.ast.ScenarioDefinition;

import java.util.List;
import java.util.stream.Collectors;

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

    public static final String ScenarioBody = String.join(
            System.getProperty("line.separator"),
            "%1$s",//headers
            "public virtual void %2$s()",//name(space & special chars removed)
            "}",
            "TechTalk.SpecFlow.ScenarioInfo scenarioInfo = new TechTalk.SpecFlow.ScenarioInfo(\"%3$s\", new string[] {%4$s});",//name, comma separate and \" tags
            "this.ScenarioSetup(scenarioInfo);",
            "%5$s",//testRunner.Given(""),testRunner.And(""),testRunner.When(""),testRunner.Then(""),
            "testRunner.CollectScenarioErrors();",
            "}");

    public static final String StepBody = "testRunner.%1$s(%2$s)";

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
    public SpecflowFileContents translate(List<ScenarioDefinition> scenarios, ITestFrameworkConstants constants) {
        String featureContent = scenarios.stream()
                .map( scenario -> {
                    String stepsString = scenario.getSteps().stream()
                            .map( step -> String.format(StepBody, step.getKeyword(), step.getText()) )
                            .collect( Collectors.joining( System.getProperty("line.separator")) );
                    return String.format(ScenarioBody,
                            constants.getTestScenarioMethodHeader(scenario.getName()),
                            scenario.getName().replaceAll("[^A-Za-z0-9]", ""),
                            scenario.getName(),
                            "",//tags
                            stepsString);
                })
                .collect( Collectors.joining( System.getProperty("line.separator")) );

        return new SpecflowFileContents(featureContent, "");
    }
}
