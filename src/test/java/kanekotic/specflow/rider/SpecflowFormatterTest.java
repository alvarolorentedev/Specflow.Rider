package kanekotic.specflow.rider;

import gherkin.formatter.Formatter;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SpecflowFormatterTest {

    @Test
    public void isInstanceOfGherkinFormatter() throws Exception {
        SpecflowFormatter formatter = new SpecflowFormatter();
        assertTrue(formatter instanceof Formatter);
    }



}