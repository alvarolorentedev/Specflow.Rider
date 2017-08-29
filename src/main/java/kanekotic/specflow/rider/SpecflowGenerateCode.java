package kanekotic.specflow.rider;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

public class SpecflowGenerateCode extends AnAction {

    private ISpecflowLexer lexer;

    public SpecflowGenerateCode(){
        this(new SpecflowLexer());
    }

    public SpecflowGenerateCode(ISpecflowLexer lexer){
        super();
        this.lexer = lexer;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        try {
            lexer.analize(file.contentsToByteArray());
        } catch (IOException ioExeption) {

        }
    }
}

