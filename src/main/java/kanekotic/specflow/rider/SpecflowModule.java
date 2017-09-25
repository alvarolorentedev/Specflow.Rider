package kanekotic.specflow.rider;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.PsiFileFactoryImpl;
import com.intellij.psi.impl.PsiManagerImpl;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import gherkin.Parser;
import gherkin.ast.GherkinDocument;
import org.codehaus.groovy.ast.builder.AstBuilder;


public class SpecflowModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<Parser<GherkinDocument>>(){}).toInstance(new Parser<GherkinDocument>(new gherkin.AstBuilder()));
        bind(ISpecflowAnalyzer.class).to(SpecflowAnalyzer.class);
        bind(IDirectoryFactory.class).to(DirectoryFactory.class);
        bind(IFileFactory.class).to(FileFactory.class);
        bind(ISpecflowTranslator.class).to(SpecflowTranslatorCSharp.class);
        bind(ITestFrameworkConstants.class).to(NunitTestFrameworkConstants.class);
    }
}
