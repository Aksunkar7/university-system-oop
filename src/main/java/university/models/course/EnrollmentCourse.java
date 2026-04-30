package university.models.course;

import university.enums.LessonType;
import university.models.users.Student;
import university.models.users.Teacher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnrollmentCourse {

    private Course course;
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Mark> marks;
    private LessonType lessonType;
    private Date enrollmentDate;

    public EnrollmentCourse(Course course, LessonType lessonType) {
        this.course = course;
        this.lessonType = lessonType;
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.enrollmentDate = new Date();
    }

    public Course getCourse() { return course; }
    public LessonType getLessonType() { return lessonType; }
    public Date getEnrollmentDate() { return enrollmentDate; }

    public List<Student> getStudents() { return students; }
    public List<Teacher> getTeachers() { return teachers; }
    public List<Mark> getMarks() { return marks; }

    public void addStudent(Student s) { students.add(s); }
    public void addTeacher(Teacher t) { teachers.add(t); }
    public void addMark(Mark m) { marks.add(m); }

    @Override
    public String toString() {
        return "EnrollmentCourse{course=" + course.getName() +
                ", lessonType=" + lessonType +
                ", students=" + students.size() +
                ", teachers=" + teachers.size() + "}";
    }
}