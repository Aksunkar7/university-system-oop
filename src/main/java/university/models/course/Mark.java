package university.models.course;

import university.models.users.Student;

public class Mark {

    private Student student;
    private Course course;
    private double att1;
    private double att2;
    private double finalMark;
    private double total;



    public Mark(Student student, Course course, double att1, double att2, double finalMark) {
        this.student = student;
        this.course = course;
        this.att1 = att1;
        this.att2 = att2;
        this.finalMark = finalMark;
        // Стандартная формула: att1 + att2 = 60%, final = 40%
        this.total = (att1 * 0.3) + (att2 * 0.3) + (finalMark * 0.4);
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public double getAtt1() { return att1; }
    public double getAtt2() { return att2; }
    public double getFinalMark() { return finalMark; }
    public double getTotal() { return total; }

    // Проходной балл = 50
    public boolean isPassed() { return total >= 50.0; }

    @Override
    public String toString() {
        return "Mark{student=" + student.getFirstName() + " " + student.getLastName() +
                ", course=" + course.getName() +
                ", att1=" + att1 + ", att2=" + att2 +
                ", final=" + finalMark + ", total=" + String.format("%.2f", total) +
                ", passed=" + isPassed() + "}";
    }
}