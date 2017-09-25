package kanekotic.specflow.rider;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class SpecflowGenerateCodeTest {

    @Test
    public void actionAnalyzesFile() throws IOException {

        AnActionEvent action = mock(AnActionEvent.class);
        ISpecflowAnalyzer lexer = mock(ISpecflowAnalyzer.class);
        PsiFileFactory psiFileFactory = mock(PsiFileFactory.class);
        PsiDirectoryFactory psiDirectoryFactory = mock(PsiDirectoryFactory.class);
        FileFactory fileFactory = mock(FileFactory.class);
        DirectoryFactory directoryFactory = mock(DirectoryFactory.class);
        VirtualFile file = mock(VirtualFile.class);
        VirtualFile directoryFile = mock(VirtualFile.class);
        PsiDirectory directory = mock(PsiDirectory.class);
        Project project = mock(Project.class);
        PsiFile contentFile = mock(PsiFile.class);
        PsiFile stepsFile = mock(PsiFile.class);
        String fileContent = Faker.getRandomString();
        String projectName = Faker.getRandomString();

        when(action.getData(PlatformDataKeys.VIRTUAL_FILE)).thenReturn(file);
        when(file.contentsToByteArray()).thenReturn(fileContent.getBytes());
        when(file.getParent()).thenReturn(directoryFile);
        when(action.getProject()).thenReturn(project);
        when(project.getName()).thenReturn(projectName);
        when(fileFactory.getInstance(project)).thenReturn(psiFileFactory);
        when(directoryFactory.getInstance(project)).thenReturn(psiDirectoryFactory);
        when(psiDirectoryFactory.createDirectory(file.getParent())).thenReturn(directory);
        SpecflowFileContents content = new SpecflowFileContents(Faker.getRandomString(), Faker.getRandomString());
        when(lexer.analyze(fileContent,projectName)).thenReturn(content);
        when(psiFileFactory.createFileFromText(file.getName()+"Feature.cs",content.feature)).thenReturn(contentFile);
        when(psiFileFactory.createFileFromText(file.getName()+"Steps.cs",content.steps)).thenReturn(stepsFile);

        SpecflowGenerateCode generator = new SpecflowGenerateCode(lexer, fileFactory, directoryFactory);
        generator.actionPerformed(action);
        verify(directory).add(contentFile);
        verify(directory).add(stepsFile);
        verify(lexer).analyze(fileContent, projectName);
    }

    @Test
    public void actionFileReadFailDoesNotBubbleExcepted() throws IOException {

        AnActionEvent action = mock(AnActionEvent.class);
        ISpecflowAnalyzer lexer = mock(ISpecflowAnalyzer.class);
        FileFactory fileFactory = mock(FileFactory.class);
        DirectoryFactory directoryFactory = mock(DirectoryFactory.class);
        VirtualFile file = mock(VirtualFile.class);

        when(action.getData(PlatformDataKeys.VIRTUAL_FILE)).thenReturn(file);
        when(file.contentsToByteArray()).thenThrow(new IOException());

        SpecflowGenerateCode generator = new SpecflowGenerateCode(lexer, fileFactory, directoryFactory);
        generator.actionPerformed(action);

        verify(lexer, never()).analyze(anyString(), anyString());
    }

}

