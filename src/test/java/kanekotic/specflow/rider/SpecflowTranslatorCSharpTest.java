package kanekotic.specflow.rider;

import gherkin.ast.Feature;
import gherkin.ast.ScenarioDefinition;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        ScenarioDefinition scenario = mock(ScenarioDefinition.class);
        List<ScenarioDefinition> scenarios = new ArrayList<>();
        {
            scenarios.add(scenario);
        }

        ITestFrameworkConstants constants = mock(ITestFrameworkConstants.class);

        SpecflowTranslatorCSharp translator = new SpecflowTranslatorCSharp();
        SpecflowFileContents contents = translator.translate(scenarios, constants);

        assertEquals(ScenarioBody, contents.feature);
        assertEquals("", contents.steps);
    }

}