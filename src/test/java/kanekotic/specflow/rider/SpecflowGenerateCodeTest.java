package kanekotic.specflow.rider;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class SpecflowGenerateCodeTest {

    @Test
    public void actionAnalyzesFile() throws IOException {

        AnActionEvent action = mock(AnActionEvent.class);
        ISpecflowAnalyzer lexer = mock(ISpecflowAnalyzer.class);
        VirtualFile file = mock(VirtualFile.class);
        Project project = mock(Project.class);
        String fileContent = Faker.getRandomString();
        String projectName = Faker.getRandomString();

        when(action.getData(PlatformDataKeys.VIRTUAL_FILE)).thenReturn(file);
        when(file.contentsToByteArray()).thenReturn(fileContent.getBytes());
        when(action.getProject()).thenReturn(project);
        when(project.getName()).thenReturn(projectName);

        SpecflowGenerateCode genrator = new SpecflowGenerateCode(lexer);
        genrator.actionPerformed(action);

        verify(lexer).analyze(fileContent, projectName);
    }

    @Test
    public void actionFileReadFailDoesNotBubbleExcepted() throws IOException {

        AnActionEvent action = mock(AnActionEvent.class);
        ISpecflowAnalyzer lexer = mock(ISpecflowAnalyzer.class);
        VirtualFile file = mock(VirtualFile.class);
        Project project = mock(Project.class);
        String fileContent = Faker.getRandomString();
        String projectName = Faker.getRandomString();

        when(action.getData(PlatformDataKeys.VIRTUAL_FILE)).thenReturn(file);
        when(file.contentsToByteArray()).thenThrow(new IOException());
        when(action.getProject()).thenReturn(project);
        when(project.getName()).thenReturn(projectName);

        SpecflowGenerateCode generator = new SpecflowGenerateCode(lexer);
        generator.actionPerformed(action);

        verify(lexer, never()).analyze(fileContent, "");
    }

}

