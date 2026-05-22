package university.models.users;

import university.enums.Language;
import university.enums.TeacherPosition;
import university.interfaces.Researcher;
import university.models.course.Course;
import university.models.research.ResearchPaper;
import university.models.research.ResearchProject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Teacher extends Employee implements Comparable<Teacher>, Researcher {

    private TeacherPosition position;
    private List<Course> courses;
    private double rating;
    private List<ResearchPaper> papers = new ArrayList<>();
    private List<ResearchProject> projects = new ArrayList<>();

    public Teacher(int id, String firstName, String lastName, String login,
                   String password, Language language,
                   double salary, String department,
                   TeacherPosition position) {
        super(id, firstName, lastName, login, password, language, salary, department);
        this.position = position;
        this.courses = new ArrayList<>();
        this.rating = 0.0;
    }

    public TeacherPosition getPosition() {
        return position;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    @Override
    public String getInfo() {
        return "Teacher: " + getFirstName() + " " + getLastName() +
                ", position: " + position +
                ", department: " + getDepartment();
    }

    @Override
    public int compareTo(Teacher other) {
        return Double.compare(other.rating, this.rating);
    }

    @Override
    public String toString() {
        return getInfo();
    }

    @Override
    public int calculateHIndex() {
        return 0;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> c) {

    }

    @Override
    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return papers;
    }

    @Override
    public List<ResearchProject> getProjects() {
        return projects;
    }
}