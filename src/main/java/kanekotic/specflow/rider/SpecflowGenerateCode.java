package kanekotic.specflow.rider;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.file.PsiDirectoryFactory;

import java.io.IOException;

public class SpecflowGenerateCode extends AnAction {

    private final IDirectoryFactory psiDirectoryFactory;
    private final IFileFactory psiFileFactory;
    private ISpecflowAnalyzer lexer;

    public SpecflowGenerateCode(){
        super();
        Injector injector = Guice.createInjector(new SpecflowModule());
        this.lexer = injector.getInstance(ISpecflowAnalyzer.class);
        this.psiFileFactory = injector.getInstance(IFileFactory.class);
        this.psiDirectoryFactory = injector.getInstance(IDirectoryFactory.class);
    }

    public SpecflowGenerateCode(ISpecflowAnalyzer lexer,IFileFactory psiFileFactory,IDirectoryFactory psiDirectoryFactory){
        super();
        this.lexer = lexer;
        this.psiFileFactory = psiFileFactory;
        this.psiDirectoryFactory = psiDirectoryFactory;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        try {
            Project project = e.getProject();
            SpecflowFileContents content = lexer.analyze(new String(file.contentsToByteArray()), project.getName());
            PsiFileFactory fileFactory = psiFileFactory.getInstance(project);
            PsiDirectory directory = psiDirectoryFactory.getInstance(project).createDirectory(file.getParent());
            directory.add(fileFactory.createFileFromText(file.getName()+"Feature.cs",content.feature));
            directory.add(fileFactory.createFileFromText(file.getName()+"Steps.cs",content.steps));
        } catch (IOException ioExeption) {

        }
    }

}

