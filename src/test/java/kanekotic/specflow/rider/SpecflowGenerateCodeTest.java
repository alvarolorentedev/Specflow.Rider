package kanekotic.specflow.rider;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SpecflowGenerateCodeTest {

    @Test
    public void actionAnalizesFile(){
        SpecflowGenerateCode genrator = new SpecflowGenerateCode();
        AnActionEvent action = mock(AnActionEvent.class);
        VirtualFile file = mock(VirtualFile.class);

        when(action.getData(PlatformDataKeys.VIRTUAL_FILE)).thenReturn(file);

        genrator.actionPerformed(action);

        verify(action).getData(PlatformDataKeys.VIRTUAL_FILE);
    }

}