package kanekotic.specflow.rider;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

public class SpecflowGenerateCode extends AnAction {

    private ISpecflowAnalyzer lexer;

    public SpecflowGenerateCode(){
        super();
        Injector injector = Guice.createInjector(new SpecflowModule());
        this.lexer = injector.getInstance(ISpecflowAnalyzer.class);
    }

    public SpecflowGenerateCode(ISpecflowAnalyzer lexer){
        super();
        this.lexer = lexer;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        try {
            lexer.analyze(new String(file.contentsToByteArray()), e.getProject().getName());
        } catch (IOException ioExeption) {

        }
    }

}

