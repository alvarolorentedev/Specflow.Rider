package kanekotic.specflow.rider;

import java.util.List;

public interface ISpecflowAnalizer {
    List<String> analize(byte[] fileContent);
}

