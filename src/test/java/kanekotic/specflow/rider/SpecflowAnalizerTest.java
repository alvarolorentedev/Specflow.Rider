package kanekotic.specflow.rider;

import org.junit.Test;

import java.util.List;

public class SpecflowAnalizerTest {

    @Test
    public void analize() throws Exception {
        SpecflowAnalizer analizer = new SpecflowAnalizer();
        List<String> newFiles = analizer.analize(new byte[5]);
        assert newFiles.isEmpty();
    }

}