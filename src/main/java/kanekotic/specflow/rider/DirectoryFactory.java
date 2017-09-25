package kanekotic.specflow.rider;

import com.intellij.openapi.project.Project;
import com.intellij.psi.impl.file.PsiDirectoryFactory;

public class DirectoryFactory implements IDirectoryFactory {
    @Override
    public PsiDirectoryFactory getInstance(Project project) {
        return PsiDirectoryFactory.getInstance(project);
    }
}
