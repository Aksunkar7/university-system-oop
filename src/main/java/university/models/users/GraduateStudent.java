package university.models.users;

import university.enums.DegreeType;
import university.enums.Language;
import university.exceptions.LowHIndexException;
import university.interfaces.Researcher;
import university.models.research.ResearchPaper;
import university.models.research.ResearchProject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GraduateStudent extends Student implements Researcher {
    private DegreeType degree;
    private Researcher supervisor;
    private List<ResearchPaper> thesisPapers;
    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;

    public GraduateStudent(int id, String firstName, String lastName, String login, String password, Language language, int year, String major, DegreeType degree) {
        super(id, firstName, lastName, login, password, language, year, major);
        this.degree = degree;
        this.thesisPapers = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public Researcher getSupervisor() { return supervisor; }

    public void setSupervisor(Researcher supervisor) throws LowHIndexException {
        if (supervisor.calculateHIndex() < 3)
            throw new LowHIndexException("Supervisor h-index must be >= 3");
        this.supervisor = supervisor;
    }

    public void addThesisPaper(ResearchPaper paper) {
        thesisPapers.add(paper);
        papers.add(paper);
    }

    @Override
    public int calculateHIndex() {
        return 0;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> c) {
        // логика в ResearchService
    }

    @Override
    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
    }

    @Override
    public List<ResearchPaper> getPapers() { return papers; }

    @Override
    public List<ResearchProject> getProjects() { return projects; }

    @Override
    public String getInfo() {
        return "GraduateStudent: " + getFirstName() + " " + getLastName() + ", Degree: " + degree;
    }

    @Override
    public String toString() { return getInfo(); }

    public DegreeType getDegree() { return degree; }
    public List<ResearchPaper> getThesisPapers() { return thesisPapers; }
}
