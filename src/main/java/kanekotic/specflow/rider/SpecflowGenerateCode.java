package kanekotic.specflow.rider;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import gherkin.AstBuilder;
import gherkin.Parser;
import gherkin.ast.GherkinDocument;
import gherkin.pickles.Compiler;

import java.io.IOException;

public class SpecflowGenerateCode extends AnAction {

    private ISpecflowAnalizer lexer;

    public SpecflowGenerateCode(){
        this(new SpecflowAnalizer(new Compiler(), new Parser<GherkinDocument>(new AstBuilder())));
    }

    public SpecflowGenerateCode(ISpecflowAnalizer lexer){
        super();
        this.lexer = lexer;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        try {
            lexer.analize(new String(file.contentsToByteArray()));
        } catch (IOException ioExeption) {

        }
    }
}

