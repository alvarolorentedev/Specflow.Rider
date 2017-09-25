package kanekotic.specflow.rider;

import gherkin.ast.Feature;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.Step;
import gherkin.ast.Tag;

import java.util.ArrayList;
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

    public static final String StepsFeatureBody = String.join(
            System.getProperty("line.separator"),
            "[Binding]",
            "public class %1$s",
            "{",
            "%2$s",
            "}");

    public static final String ScenarioBody = String.join(
            System.getProperty("line.separator"),
            "%1$s",
            "public virtual void %2$s()",
            "}",
            "TechTalk.SpecFlow.ScenarioInfo scenarioInfo = new TechTalk.SpecFlow.ScenarioInfo(\"%3$s\", new string[] {%4$s});",
            "this.ScenarioSetup(scenarioInfo);",
            "%5$s",
            "testRunner.CollectScenarioErrors();",
            "}");

    public static final String StepBody = "testRunner.%1$s(%2$s)";

    public static final String StepsStepsBody = String.join(
            System.getProperty("line.separator"),
            "[%1$s(@\"%2$s\")]",
            "public void %3$s",
            "{",
            "throw new NotImplementedException();",
            "}");

    @Override
    public SpecflowFileContents translate(Feature content, SpecflowFileContents scenarios, ITestFrameworkConstants constants) {
        String featureContent = String.format(BodyContent,
                constants.getExpectedClassAttributes(content.getName()),
                content.getName().replaceAll("[^A-Za-z0-9]", ""),
                constants.getTestFixtureSetupHeader(),
                content.getName(),
                content.getDescription(),
                constants.getTestFixtureTearDownHeader(),
                constants.getTestScenarioSetupHeader(),
                constants.getTestScenarioTearDownHeader(),
                scenarios.feature);
        String stepsContent = String.format(StepsFeatureBody,
                content.getName().replaceAll("[^A-Za-z0-9]", ""),
                scenarios.steps);
        return new SpecflowFileContents(featureContent,stepsContent);
    }

    @Override
    public SpecflowFileContents translate(List<ScenarioDefinition> scenarios, List<Tag> tags, ITestFrameworkConstants constants) {
        String featureContent = scenarios.stream()
                .map( scenario -> {
                    String stepsString = scenario.getSteps().stream()
                            .map( step -> String.format(StepBody, step.getKeyword(), step.getText()) )
                            .collect( Collectors.joining( System.getProperty("line.separator")) );
                    String tagsString = tags.stream()
                            .map(tg -> String.format("\"%1$s\"", tg.getName()))
                            .collect(Collectors.joining(","));
                    return String.format(ScenarioBody,
                            constants.getTestScenarioMethodHeader(scenario.getName()),
                            scenario.getName().replaceAll("[^A-Za-z0-9]", ""),
                            scenario.getName(),
                            tagsString,
                            stepsString);
                })
                .collect( Collectors.joining( System.getProperty("line.separator")) );
        List<Step> stepsDone= new ArrayList<>();
        String stepString = scenarios.stream()
                .map(ScenarioDefinition::getSteps)
                .flatMap(List::stream)
                .filter(step -> !stepsDone.stream().anyMatch(stepDone -> step.getText() == stepDone.getText() && step.getKeyword() == stepDone.getKeyword()))
                .peek(step -> stepsDone.add(step))
                .map(step -> String.format(StepsStepsBody,
                        step.getKeyword(),
                        step.getText(),
                        step.getText().replaceAll("[^A-Za-z0-9]", "")))
                .collect( Collectors.joining( System.getProperty("line.separator")) );

        return new SpecflowFileContents(featureContent, stepString);
    }


    public static final String StepsFileBody = String.join(
            System.getProperty("line.separator"),
            "namespace %1$s",
            "{",
            "using TechTalk.SpecFlow;",
            " %2$s",
            "}");

    public static final String FeatureFileBody = String.join(
            System.getProperty("line.separator"),
            "using NUnit.Framework;",
            "using TechTalk.SpecFlow;",
            "namespace %1$s",
            "{",
            " %2$s",
            "}");

    @Override
    public SpecflowFileContents translate(String namespace, SpecflowFileContents feature, ITestFrameworkConstants constants) {
        String featureContent = String.format(FeatureFileBody,
                namespace,
                feature.feature);
        String stepsContent = String.format(StepsFileBody,
                namespace,
                feature.steps);
        return new SpecflowFileContents(featureContent,stepsContent);
    }
}
