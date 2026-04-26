package university.models.users;

import university.enums.Language;
import java.util.ArrayList;
import java.util.List;

public class Student extends User implements Comparable<Student> {
    private double gpa;
    private int credits;
    private int failCount;
    private int year;
    private String major;
    private List<Object> courses;
    private List<Object> marks;

    public Student(int id, String firstName, String lastName, String login, String password, Language language, int year, String major) {
        super(id, firstName, lastName, login, password, language);
        this.year = year;
        this.major = major;
        this.gpa = 0.0;
        this.credits = 0;
        this.failCount = 0;
        this.courses = new ArrayList<>();
        this.marks = new ArrayList<>();
    }

    @Override
    public int compareTo(Student other) {
        return Double.compare(other.gpa, this.gpa);
    }

    @Override
    public String getInfo() {
        return "Student: " + getFirstName() + " " + getLastName() + ", GPA: " + gpa + ", Credits: " + credits;
    }

    @Override
    public String toString() {
        return getInfo();
    }

    public double getGpa() { return gpa; }
    public int getCredits() { return credits; }
    public int getFailCount() { return failCount; }
    public int getYear() { return year; }
    public String getMajor() { return major; }
    public List<Object> getCourses() { return courses; }
    public List<Object> getMarks() { return marks; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setFailCount(int failCount) { this.failCount = failCount; }
    public void setYear(int year) { this.year = year; }
    public void setMajor(String major) { this.major = major; }
}