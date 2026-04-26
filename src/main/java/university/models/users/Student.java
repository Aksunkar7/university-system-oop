package university.models.users;

import university.enums.Language;
import university.exceptions.CreditLimitException;
import university.exceptions.FailLimitException;
import java.util.ArrayList;
import java.util.List;

public class Student extends User implements Comparable<Student> {
    private double gpa;
    private int credits;
    private int failCount;
    private int year;
    private String major;
    private List<String> courses;

    public Student(int id, String firstName, String lastName, String login, String password, Language language, int year, String major) {
        super(id, firstName, lastName, login, password, language);
        this.year = year;
        this.major = major;
        this.gpa = 0.0;
        this.credits = 0;
        this.failCount = 0;
        this.courses = new ArrayList<>();
    }

    public void registerCourse(String course, int courseCredits) throws CreditLimitException, FailLimitException {
        if (failCount >= 3) throw new FailLimitException("Student exceeded fail limit");
        if (credits + courseCredits > 21) throw new CreditLimitException("Credit limit exceeded: max 21");
        courses.add(course);
        credits += courseCredits;
        System.out.println(getFirstName() + " registered for " + course);
    }

    public void viewMarks() {
        System.out.println("Viewing marks for " + getFirstName());
    }

    public String getTranscript() {
        return "Transcript of " + getFirstName() + " " + getLastName() + ", GPA: " + gpa;
    }

    public void rateTeacher(String teacher, double rating) {
        System.out.println(getFirstName() + " rated " + teacher + ": " + rating);
    }

    public void viewCourses() {
        System.out.println("Courses: " + courses);
    }

    public void viewAttendance() {
        System.out.println("Viewing attendance for " + getFirstName());
    }

    public void viewSchedule() {
        System.out.println("Viewing schedule for " + getFirstName());
    }

    @Override
    public int compareTo(Student other) {
        return Double.compare(other.gpa, this.gpa);
    }

    @Override
    public String getInfo() {
        return "Student: " + getFirstName() + " " + getLastName() + ", GPA: " + gpa + ", Credits: " + credits;
    }

    public double getGpa() { return gpa; }
    public int getCredits() { return credits; }
    public int getFailCount() { return failCount; }
    public int getYear() { return year; }
    public String getMajor() { return major; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public void setFailCount(int failCount) { this.failCount = failCount; }
}
