package kanekotic.specflow.rider;

public class NunitTestFrameworkConstants implements ITestFrameworkConstants {
    @Override
    public String getExpectedClassAtributes() { return ExpectedClassAtributes; }

    @Override
    public String getTestFixtureSetupHeader() {
        return TestFixtureSetupHeader;
    }

    @Override
    public String getTestFixtureTearDownHeader() {
        return TestFixtureTearDownHeader;
    }

    @Override
    public String getTestScenarioSetupHeader() {
        return TestScenrioSetupHeader;
    }

    @Override
    public String getTestScenarioTearDownHeader() {
        return TestScenrioTearDownHeader;
    }

    public static final String  ExpectedClassAtributes = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TestFixtureAttribute()]",
            "[NUnit.Framework.DescriptionAttribute(\"{0}\")]"//name
    );
    public static final String  TestFixtureSetupHeader = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TestFixtureSetUpAttribute()]",
            "public virtual void FeatureSetup()"
    );
    public static final String  TestFixtureTearDownHeader = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TestFixtureTearDownAttribute()]",
            "public virtual void FeatureTearDown()"
    );
    public static final String  TestScenrioSetupHeader = "public virtual void ScenarioSetup(TechTalk.SpecFlow.ScenarioInfo scenarioInfo)";
    public static final String  TestScenrioTearDownHeader = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TearDownAttribute()]",
            "public virtual void ScenarioTearDown()"
    );
}
