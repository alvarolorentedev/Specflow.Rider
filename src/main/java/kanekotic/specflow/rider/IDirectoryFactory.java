package kanekotic.specflow.rider;

import com.intellij.openapi.project.Project;
import com.intellij.psi.impl.file.PsiDirectoryFactory;

public interface IDirectoryFactory {
    PsiDirectoryFactory getInstance(Project project);
}
