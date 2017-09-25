package kanekotic.specflow.rider;

import gherkin.ast.Feature;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.Step;
import gherkin.ast.Tag;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SpecflowTranslatorCSharpTest
{

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
            "%1$s",
            "public virtual void %2$s()",
            "}",
            "TechTalk.SpecFlow.ScenarioInfo scenarioInfo = new TechTalk.SpecFlow.ScenarioInfo(\"%3$s\", new string[] {%4$s});",
            "this.ScenarioSetup(scenarioInfo);",
            "%5$s",
            "testRunner.CollectScenarioErrors();",
            "}");

    public static final String StepsFeatureBody = String.join(
            System.getProperty("line.separator"),
            "[Binding]",
            "public class %1$s",
            "{",
            "%2$s",
            "}");

    public static final String StepsStepsBody = String.join(
            System.getProperty("line.separator"),
            "[%1$s(@\"%2$s\")]",
            "public void %3$s",
            "{",
            "throw new NotImplementedException();",
            "}");

    public static final String StepBody = "testRunner.%1$s(%2$s)";

    private String getRandomString(){
        return UUID.randomUUID().toString();
    }

    @Test
    public void translateFeatureTest() {

        SpecflowTranslatorCSharp translator = new SpecflowTranslatorCSharp();

        Feature feature = mock(Feature.class);
        ITestFrameworkConstants constants = mock(ITestFrameworkConstants.class);

        when(feature.getName()).thenReturn(getRandomString().replace('-', ' ') + "("+getRandomString()+"<>!)");
        when(feature.getDescription()).thenReturn(getRandomString().replace('-', ' ') + "("+getRandomString()+"<>!)");
        when(constants.getExpectedClassAttributes(feature.getName())).thenReturn(getRandomString());
        when(constants.getTestFixtureSetupHeader()).thenReturn(getRandomString());
        when(constants.getTestFixtureTearDownHeader()).thenReturn(getRandomString());
        when(constants.getTestScenarioSetupHeader()).thenReturn(getRandomString());
        when(constants.getTestScenarioTearDownHeader()).thenReturn(getRandomString());

        SpecflowFileContents scenarionContent = new SpecflowFileContents();
        scenarionContent.feature = getRandomString();
        scenarionContent.steps = getRandomString();

        SpecflowFileContents contents = translator.translate(feature, scenarionContent, constants);
        String ExpectedFeatureContent = String.format(BodyContent,
                constants.getExpectedClassAttributes(feature.getName()),
                feature.getName().replaceAll("[^A-Za-z0-9]", ""),
                constants.getTestFixtureSetupHeader(),
                feature.getName(),
                feature.getDescription(),
                constants.getTestFixtureTearDownHeader(),
                constants.getTestScenarioSetupHeader(),
                constants.getTestScenarioTearDownHeader(),
                scenarionContent.feature);

        String ExpectedStepsContent = String.format(StepsFeatureBody,
                feature.getName().replaceAll("[^A-Za-z0-9]", ""),
                scenarionContent.steps);
        assertEquals(ExpectedFeatureContent, contents.feature);
        assertEquals(ExpectedStepsContent, contents.steps);
    }

    @Test
    public void translateScenariosTest(){

        ITestFrameworkConstants constants = mock(ITestFrameworkConstants.class);
        ScenarioDefinition scenario = mock(ScenarioDefinition.class);
        when(scenario.getName()).thenReturn(getRandomString().replace('-', ' ') + "("+getRandomString()+"<>!)");
        when(scenario.getDescription()).thenReturn(getRandomString().replace('-', ' ') + "("+getRandomString()+"<>!)");
        List<ScenarioDefinition> scenarios = new ArrayList<>();
        {
            scenarios.add(scenario);
            scenarios.add(scenario);
        }
        Tag tag = mock(Tag.class);
        when(tag.getName()).thenReturn(getRandomString());
        List<Tag> tags = new ArrayList<>();
        {
            tags.add(tag);
            tags.add(tag);
        }
        Step given = mock(Step.class);
        Step when = mock(Step.class);
        Step and = mock(Step.class);
        Step then = mock(Step.class);

        when(given.getKeyword()).thenReturn("Given");
        when(given.getText()).thenReturn("something doing something like" + getRandomString());
        when(when.getKeyword()).thenReturn("When");
        when(when.getText()).thenReturn("that something happen with" + getRandomString());
        when(and.getKeyword()).thenReturn("And");
        when(and.getText()).thenReturn("something else happen with" + getRandomString());
        when(then.getKeyword()).thenReturn("Then");
        when(then.getText()).thenReturn("the result is something like"+ getRandomString());
        when(constants.getTestScenarioMethodHeader(scenario.getName())).thenReturn(getRandomString());
        List<Step> steps = new ArrayList<>();
        {
            steps.add(given);
            steps.add(when);
            steps.add(and);
            steps.add(then);
        }
        when(scenario.getSteps()).thenReturn(steps);
        String scenariosString = scenarios.stream()
                .map( scene -> {
                    String stepsString = scene.getSteps().stream()
                            .map( step -> String.format(StepBody, step.getKeyword(), step.getText()) )
                            .collect( Collectors.joining( System.getProperty("line.separator")) );
                    String tagsString = tags.stream()
                            .map(tg -> String.format("\"%1$s\"", tg.getName()))
                            .collect(Collectors.joining(","));
                    return String.format(ScenarioBody,
                        constants.getTestScenarioMethodHeader(scene.getName()),
                        scene.getName().replaceAll("[^A-Za-z0-9]", ""),
                        scene.getName(),
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

        SpecflowTranslatorCSharp translator = new SpecflowTranslatorCSharp();

        SpecflowFileContents contents = translator.translate(scenarios, tags, constants);

        assertEquals(scenariosString, contents.feature);
        assertEquals(stepString, contents.steps);
    }

}