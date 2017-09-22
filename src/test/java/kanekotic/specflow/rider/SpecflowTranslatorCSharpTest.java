package kanekotic.specflow.rider;

import gherkin.ast.Feature;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.Step;
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
            "%1$s",//headers
            "public virtual void %2$s()",//name(space & special chars removed)
            "}",
            "TechTalk.SpecFlow.ScenarioInfo scenarioInfo = new TechTalk.SpecFlow.ScenarioInfo(\"%3$s\", new string[] {%4$s});",//name, comma separate and \" tags
            "this.ScenarioSetup(scenarioInfo);",
            "%5$s",//testRunner.Given(""),testRunner.And(""),testRunner.When(""),testRunner.Then(""),
            "testRunner.CollectScenarioErrors();",
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
        scenarionContent.feature = UUID.randomUUID().toString();

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
        assertEquals(ExpectedFeatureContent, contents.feature);
        assertEquals("", contents.steps);
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
                    return String.format(ScenarioBody,
                        constants.getTestScenarioMethodHeader(scene.getName()),
                        scene.getName().replaceAll("[^A-Za-z0-9]", ""),
                        scene.getName(),
                        "",//tags
                        stepsString);
                })
                .collect( Collectors.joining( System.getProperty("line.separator")) );

        SpecflowTranslatorCSharp translator = new SpecflowTranslatorCSharp();

        SpecflowFileContents contents = translator.translate(scenarios, constants);

        assertEquals(scenariosString, contents.feature);
        assertEquals("", contents.steps);
    }

}