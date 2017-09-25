package kanekotic.specflow.rider;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;

public interface IFileFactory {
    PsiFileFactory getInstance(Project project);
}
