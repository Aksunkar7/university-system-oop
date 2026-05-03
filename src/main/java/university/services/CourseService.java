package university.services;

import university.models.course.*;
import university.models.users.Student;
import university.models.users.Teacher;
import university.patterns.DataStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseService {

    private DataStorage db = DataStorage.getInstance();

  
    private List<Schedule> schedules = new ArrayList<>();
    private List<Attendance> attendances = new ArrayList<>();

    /**
     * Регистрация студента на курс.
     * Проверяет: credits <= 21, failCount <= 3
     */
    public EnrollmentCourse registerStudentToCourse(Student s, Course c) throws Exception {

        int totalAfterEnroll = s.getCredits() + c.getCredits();
        if (totalAfterEnroll > 21) {
            throw new Exception("CreditLimitException: Student would exceed 21 credits");
        }
        if (s.getFailCount() > 3) {
            throw new Exception("FailLimitException: Student exceeded fail limit");
        }

        EnrollmentCourse enrollment = findEnrollment(c);
        if (enrollment == null) {
            enrollment = new EnrollmentCourse(c, null);
            db.getEnrollments().add(enrollment);
        }

        enrollment.addStudent(s);
        return enrollment;
    }


    // Выставление оценки студенту.

    public Mark putMark(Student s, Course c, double att1, double att2, double finalMark) {
        Mark mark = new Mark(s, c, att1, att2, finalMark);

        EnrollmentCourse enrollment = findEnrollment(c);
        if (enrollment == null) {
            enrollment = new EnrollmentCourse(c, null);
            db.getEnrollments().add(enrollment);
        }

        enrollment.addMark(mark);
        return mark;
    }


     // Назначение учителя на курс.

    public void assignTeacherToCourse(Teacher t, Course c) {
        EnrollmentCourse enrollment = findEnrollment(c);
        if (enrollment == null) {
            enrollment = new EnrollmentCourse(c, null);
            db.getEnrollments().add(enrollment);
        }

        enrollment.addTeacher(t);
        t.addCourse(c);
    }


    // Генерация расписания.

    public Schedule generateSchedule(Course c, Room r) {
        Teacher assignedTeacher = null;
        EnrollmentCourse enrollment = findEnrollment(c);
        if (enrollment != null && !enrollment.getTeachers().isEmpty()) {
            assignedTeacher = enrollment.getTeachers().get(0);
        }

        Schedule schedule = new Schedule(c, assignedTeacher, r, new Date());
        schedules.add(schedule);
        return schedule;
    }


     // Отметка посещаемости.

    public Attendance markAttendance(Student s, Course c, boolean isPresent) {
        Attendance attendance = new Attendance(s, c, new Date(), isPresent);
        attendances.add(attendance);
        return attendance;
    }


    // Транскрипт студента.

    public String getTranscript(Student s) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("TRANSCRIPT: ").append(s.getFirstName()).append(" ").append(s.getLastName()).append("\n");
        sb.append("GPA: ").append(s.getGpa()).append(" | Credits: ").append(s.getCredits()).append("\n");
        sb.append("========================================\n");

        boolean found = false;
        for (Object obj : db.getEnrollments()) {
            EnrollmentCourse ec = (EnrollmentCourse) obj;
            for (Mark m : ec.getMarks()) {
                if (m.getStudent().equals(s)) {
                    sb.append(String.format("%-25s | ATT1: %5.1f | ATT2: %5.1f | FINAL: %5.1f | TOTAL: %5.1f | %s\n",
                            ec.getCourse().getName(),
                            m.getAtt1(), m.getAtt2(), m.getFinalMark(),
                            m.getTotal(),
                            m.isPassed() ? "PASS" : "FAIL"));
                    found = true;
                }
            }
        }

        if (!found) sb.append("No grades found.\n");
        sb.append("========================================\n");
        return sb.toString();
    }


     // Отчёт учителя по курсу.

    public String generateReport(Teacher t, Course c) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("REPORT by: ").append(t.getFirstName()).append(" ").append(t.getLastName()).append("\n");
        sb.append("Course: ").append(c.getName()).append("\n");
        sb.append("========================================\n");

        EnrollmentCourse ec = findEnrollment(c);
        if (ec == null) {
            sb.append("No enrollment data found.\n");
        } else {
            sb.append("Students enrolled: ").append(ec.getStudents().size()).append("\n");
            int passed = 0, failed = 0;
            for (Mark m : ec.getMarks()) {
                sb.append(String.format("%-20s | Total: %5.1f | %s\n",
                        m.getStudent().getFirstName() + " " + m.getStudent().getLastName(),
                        m.getTotal(),
                        m.isPassed() ? "PASS" : "FAIL"));
                if (m.isPassed()) passed++; else failed++;
            }
            sb.append("\nPassed: ").append(passed).append(" | Failed: ").append(failed).append("\n");
        }

        sb.append("========================================\n");
        return sb.toString();
    }


    // Одобрить курс для регистрации.

    public void approveCourseRegistration(Course c) {
        db.getCourses().add(c);
        System.out.println("Course approved for registration: " + c.getName());
    }


    // Добавить курс для регистрации (если ещё нет).

    public void addCourseForRegistration(Course c) {
        if (!db.getCourses().contains(c)) {
            db.getCourses().add(c);
            System.out.println("Course added for registration: " + c.getName());
        }
    }



    private EnrollmentCourse findEnrollment(Course c) {
        for (Object obj : db.getEnrollments()) {
            EnrollmentCourse ec = (EnrollmentCourse) obj;
            if (ec.getCourse().getId() == c.getId()) return ec;
        }
        return null;
    }


    // Просмотр расписания студента — все курсы на которые он записан.

    public List<Schedule> getScheduleForStudent(Student s) {
        List<Schedule> result = new ArrayList<>();
        for (Schedule sch : schedules) {
            EnrollmentCourse ec = findEnrollment(sch.getCourse());
            if (ec != null && ec.getStudents().contains(s)) {
                result.add(sch);
            }
        }
        return result;
    }


    // Просмотр расписания по конкретному курсу.

    public List<Schedule> getScheduleForCourse(Course c) {
        List<Schedule> result = new ArrayList<>();
        for (Schedule sch : schedules) {
            if (sch.getCourse().getId() == c.getId()) {
                result.add(sch);
            }
        }
        return result;
    }

    public List<Schedule> getSchedules() { return schedules; }
    public List<Attendance> getAttendances() { return attendances; }
}
