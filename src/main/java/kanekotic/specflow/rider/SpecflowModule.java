package kanekotic.specflow.rider;

import com.google.inject.AbstractModule;
import gherkin.Parser;
import org.codehaus.groovy.ast.builder.AstBuilder;


public class SpecflowModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AstBuilder.class).toInstance(new AstBuilder());
        bind(Parser.class).to(Parser.class);

        bind(ISpecflowAnalyzer.class).to(SpecflowAnalyzer.class);
        bind(ISpecflowTranslator.class).to(SpecflowTranslatorCSharp.class);
        bind(ITestFrameworkConstants.class).to(NunitTestFrameworkConstants.class);
    }
}
