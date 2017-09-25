package kanekotic.specflow.rider;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;

public class FileFactory implements IFileFactory {
    @Override
    public PsiFileFactory getInstance(Project project) {
        return PsiFileFactory.getInstance(project);
    }
}
