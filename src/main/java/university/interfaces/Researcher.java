package university.interfaces;

import university.models.research.ResearchPaper;
import university.models.research.ResearchProject;
import java.util.Comparator;
import java.util.List;

public interface Researcher {
    int calculateHIndex();
    void printPapers(Comparator<ResearchPaper> c);
    void addPaper(ResearchPaper paper);
    List<ResearchPaper> getPapers();
    List<ResearchProject> getProjects();
}
