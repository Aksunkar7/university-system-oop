package university.models.users;

import university.enums.DegreeType;
import university.enums.Language;
import university.exceptions.LowHIndexException;
import university.interfaces.Researcher;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GraduateStudent extends Student implements Researcher {
    private DegreeType degree;
    private Researcher supervisor;
    private List<Object> thesisPapers;
    private List<Object> papers;
    private List<Object> projects;

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

    public void addThesisPaper(Object paper) {
        thesisPapers.add(paper);
        papers.add(paper);
        System.out.println(getFirstName() + " added thesis paper");
    }

    @Override
    public int calculateHIndex() {
        int h = 0;
        return h;
    }

    @Override
    public void printPapers(Comparator c) {
        System.out.println("Papers of " + getFirstName() + ": " + papers);
    }

    @Override
    public void addPaper(Object paper) {
        papers.add(paper);
    }

    @Override
    public List getPapers() { return papers; }

    @Override
    public List getProjects() { return projects; }

    @Override
    public String getInfo() {
        return "GraduateStudent: " + getFirstName() + " " + getLastName() + ", Degree: " + degree;
    }

    public DegreeType getDegree() { return degree; }
}
