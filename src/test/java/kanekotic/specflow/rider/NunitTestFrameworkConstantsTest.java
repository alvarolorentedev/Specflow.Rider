package kanekotic.specflow.rider;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class NunitTestFrameworkConstantsTest {
    public static final String nunitExpectedClassAttributes = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TestFixtureAttribute()]",
            "[NUnit.Framework.DescriptionAttribute(\"%1$s\")]"
    );
    public static final String  nunitTestFixtureSetupHeader = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TestFixtureSetUpAttribute()]",
            "public virtual void FeatureSetup()"
    );
    public static final String  nunitTestFixtureTearDownHeader = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TestFixtureTearDownAttribute()]",
            "public virtual void FeatureTearDown()"
    );
    public static final String nunitTestScenarioSetupHeader = "public virtual void ScenarioSetup(TechTalk.SpecFlow.ScenarioInfo scenarioInfo)";
    public static final String nunitTestScenarioTearDownHeader = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TearDownAttribute()]",
            "public virtual void ScenarioTearDown()"
    );

    private String getRandomString(){
        return UUID.randomUUID().toString();
    }

    @Test
    public void getExpectedClassAtributesTest() {
        NunitTestFrameworkConstants constants = new NunitTestFrameworkConstants();
        String id = getRandomString();
        assertEquals(String.format(nunitExpectedClassAttributes, id),constants.getExpectedClassAttributes(id));

    }

    @Test
    public void getTestFixtureSetupHeaderTest() {
        NunitTestFrameworkConstants constants = new NunitTestFrameworkConstants();
        assertEquals(nunitTestFixtureSetupHeader,constants.getTestFixtureSetupHeader());

    }

    @Test
    public void getTestFixtureTearDownHeaderTest() {
        NunitTestFrameworkConstants constants = new NunitTestFrameworkConstants();
        assertEquals(nunitTestFixtureTearDownHeader,constants.getTestFixtureTearDownHeader());
    }

    @Test
    public void getTestScenrioSetupHeaderTest() {
        NunitTestFrameworkConstants constants = new NunitTestFrameworkConstants();
        assertEquals(nunitTestScenarioSetupHeader,constants.getTestScenarioSetupHeader());
    }

    @Test
    public void getTestScenrioTearDownHeaderTest() {
        NunitTestFrameworkConstants constants = new NunitTestFrameworkConstants();
        assertEquals(nunitTestScenarioTearDownHeader,constants.getTestScenarioTearDownHeader());
    }

}