package university.models.course;

import university.models.users.Student;

import java.util.Date;

public class Attendance {

    private Student student;
    private Course course;
    private Date date;
    private boolean isPresent;

    public Attendance(Student student, Course course, Date date, boolean isPresent) {
        this.student = student;
        this.course = course;
        this.date = date;
        this.isPresent = isPresent;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public Date getDate() { return date; }
    public boolean isPresent() { return isPresent; }

    public void setPresent(boolean present) { isPresent = present; }

    @Override
    public String toString() {
        return "Attendance{student=" + student.getFirstName() + " " + student.getLastName() +
                ", course=" + course.getName() +
                ", date=" + date +
                ", present=" + isPresent + "}";
    }
}