package kanekotic.specflow.rider;

public interface ITestFrameworkConstants {
    String getExpectedClassAttributes(String name);

    String getTestFixtureSetupHeader();

    String getTestFixtureTearDownHeader();

    String getTestScenarioSetupHeader();

    String getTestScenarioTearDownHeader();
}
