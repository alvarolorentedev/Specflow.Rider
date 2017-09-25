package kanekotic.specflow.rider;

public interface ISpecflowAnalyzer {
    SpecflowFileContents analyze(String file, String namespace);
}

