package kanekotic.specflow.rider;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NunitTestFrameworkConstantsTest {
    public static final String  nunitExpectedClassAtributes = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TestFixtureAttribute()]",
            "[NUnit.Framework.DescriptionAttribute(\"{0}\")]"//name
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
    public static final String  nunitTestScenrioSetupHeader = "public virtual void ScenarioSetup(TechTalk.SpecFlow.ScenarioInfo scenarioInfo)";
    public static final String  nunitTestScenrioTearDownHeader = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TearDownAttribute()]",
            "public virtual void ScenarioTearDown()"
    );

    @Test
    public void getExpectedClassAtributesTest() {
        NunitTestFrameworkConstants constants = new NunitTestFrameworkConstants();
        assertEquals(nunitExpectedClassAtributes,constants.getExpectedClassAtributes());

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
        assertEquals(nunitTestScenrioSetupHeader,constants.getTestScenarioSetupHeader());
    }

    @Test
    public void getTestScenrioTearDownHeaderTest() {
        NunitTestFrameworkConstants constants = new NunitTestFrameworkConstants();
        assertEquals(nunitTestScenrioTearDownHeader,constants.getTestScenarioTearDownHeader());
    }

}