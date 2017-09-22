package kanekotic.specflow.rider;

public class NunitTestFrameworkConstants implements ITestFrameworkConstants {
    @Override
    public String getExpectedClassAttributes(String name) { return  String.format(ExpectedClassAttributes,name); }

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
        return TestScenarioSetupHeader;
    }

    @Override
    public String getTestScenarioTearDownHeader() {
        return TestScenarioTearDownHeader;
    }

    @Override
    public String getTestScenarioMethodHeader(String name) { return  String.format(TestScenarioMethodHeader,name); }

    private static final String ExpectedClassAttributes = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TestFixtureAttribute()]",
            "[NUnit.Framework.DescriptionAttribute(\"%1$s\")]"
    );
    private static final String  TestFixtureSetupHeader = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TestFixtureSetUpAttribute()]",
            "public virtual void FeatureSetup()"
    );
    private static final String  TestFixtureTearDownHeader = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TestFixtureTearDownAttribute()]",
            "public virtual void FeatureTearDown()"
    );
    private static final String TestScenarioSetupHeader = "public virtual void ScenarioSetup(TechTalk.SpecFlow.ScenarioInfo scenarioInfo)";
    private static final String TestScenarioTearDownHeader = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TearDownAttribute()]",
            "public virtual void ScenarioTearDown()"
    );

    private static final String TestScenarioMethodHeader = String.join(
            System.getProperty("line.separator"),
            "[NUnit.Framework.TestAttribute()]",
            "[NUnit.Framework.DescriptionAttribute(\"%1$s\")]"
    );
}
