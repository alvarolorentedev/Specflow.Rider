package kanekotic.specflow.rider;

public class SpecflowFileContents {
    public String feature;
    public String steps;


    public SpecflowFileContents() {
        this("", "");
    }

    public SpecflowFileContents(String feature, String steps) {
        this.feature = feature;
        this.steps = steps;
    }
}
