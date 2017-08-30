package kanekotic.specflow.rider;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class SpecflowGenerateCodeTest {

    @Test
    public void actionAnalizesFile() throws IOException {

        AnActionEvent action = mock(AnActionEvent.class);
        ISpecflowAnalizer lexer = mock(ISpecflowAnalizer.class);
        VirtualFile file = mock(VirtualFile.class);
        byte[] fileContent = new byte[5];

        when(action.getData(PlatformDataKeys.VIRTUAL_FILE)).thenReturn(file);
        when(file.contentsToByteArray()).thenReturn(fileContent);

        SpecflowGenerateCode genrator = new SpecflowGenerateCode(lexer);
        genrator.actionPerformed(action);

        verify(lexer).analize(fileContent);
    }

    @Test
    public void actionFileReadFailDoesNotBubleExceptio() throws IOException {

        AnActionEvent action = mock(AnActionEvent.class);
        ISpecflowAnalizer lexer = mock(ISpecflowAnalizer.class);
        VirtualFile file = mock(VirtualFile.class);
        byte[] fileContent = new byte[5];

        when(action.getData(PlatformDataKeys.VIRTUAL_FILE)).thenReturn(file);
        when(file.contentsToByteArray()).thenThrow(new IOException());

        SpecflowGenerateCode genrator = new SpecflowGenerateCode(lexer);
        genrator.actionPerformed(action);

        verify(lexer, never()).analize(fileContent);
    }

}

