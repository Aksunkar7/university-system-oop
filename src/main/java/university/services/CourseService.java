package university.services;

import university.models.course.*;
import university.models.users.Student;
import university.models.users.Teacher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseService {

    private List<EnrollmentCourse> enrollments = new ArrayList<>();
    private List<Schedule> schedules = new ArrayList<>();
    private List<Attendance> attendances = new ArrayList<>();

    /**
     * Регистрация студента на курс.
     * Проверяет: credits <= 21, failCount <= 3
     */
    public EnrollmentCourse registerStudentToCourse(Student s, Course c) throws Exception {
        // Проверка кредитного лимита
        int totalAfterEnroll = s.getCredits() + c.getCredits();
        if (totalAfterEnroll > 21) {
            throw new Exception("CreditLimitException: student " +
                    s.getFirstName() + " would exceed 21 credits (current: " +
                    s.getCredits() + ", course: " + c.getCredits() + ")");
        }

        // Проверка количества провалов
        if (s.getFailCount() > 3) {
            throw new Exception("FailLimitException: student " +
                    s.getFirstName() + " has " + s.getFailCount() + " fails (max: 3)");
        }

        // Найти или создать EnrollmentCourse для этого курса
        EnrollmentCourse enrollment = findEnrollment(c);
        if (enrollment == null) {
            enrollment = new EnrollmentCourse(c, null);
            enrollments.add(enrollment);
        }

        enrollment.addStudent(s);
        return enrollment;
    }

    /**
     * Выставление оценки студенту.
     * Создаёт Mark и добавляет в EnrollmentCourse.
     */
    public Mark putMark(Student s, Course c, double att1, double att2, double finalMark) {
        Mark mark = new Mark(s, c, att1, att2, finalMark);

        EnrollmentCourse enrollment = findEnrollment(c);
        if (enrollment == null) {
            enrollment = new EnrollmentCourse(c, null);
            enrollments.add(enrollment);
        }

        enrollment.addMark(mark);
        return mark;
    }

    /**
     * Назначение учителя на курс.
     */
    public void assignTeacherToCourse(Teacher t, Course c) {
        EnrollmentCourse enrollment = findEnrollment(c);
        if (enrollment == null) {
            enrollment = new EnrollmentCourse(c, null);
            enrollments.add(enrollment);
        }

        enrollment.addTeacher(t);
        t.addCourse(c);
    }

    /**
     * Генерация расписания для курса и аудитории.
     */
    public Schedule generateSchedule(Course c, Room r) {
        // Находим назначенного учителя если есть
        Teacher assignedTeacher = null;
        EnrollmentCourse enrollment = findEnrollment(c);
        if (enrollment != null && !enrollment.getTeachers().isEmpty()) {
            assignedTeacher = enrollment.getTeachers().get(0);
        }

        Schedule schedule = new Schedule(c, assignedTeacher, r, new Date(), "Monday");
        schedules.add(schedule);
        return schedule;
    }

    /**
     * Отметка посещаемости студента.
     */
    public Attendance markAttendance(Student s, Course c, boolean isPresent) {
        Attendance attendance = new Attendance(s, c, new Date(), isPresent);
        attendances.add(attendance);
        return attendance;
    }

    /**
     * Транскрипт студента — все его оценки по всем курсам.
     */
    public String getTranscript(Student s) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("TRANSCRIPT: ").append(s.getFirstName()).append(" ").append(s.getLastName()).append("\n");
        sb.append("GPA: ").append(s.getGpa()).append(" | Credits: ").append(s.getCredits()).append("\n");
        sb.append("========================================\n");

        boolean found = false;
        for (EnrollmentCourse ec : enrollments) {
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

        if (!found) {
            sb.append("No grades found.\n");
        }

        sb.append("========================================\n");
        return sb.toString();
    }

    /**
     * Отчёт учителя по курсу.
     */
    public String generateReport(Teacher t, Course c) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("REPORT by: ").append(t.getFirstName()).append(" ").append(t.getLastName()).append("\n");
        sb.append("Course: ").append(c.getName()).append("\n");
        sb.append("========================================\n");

        EnrollmentCourse ec = findEnrollment(c);
        if (ec == null) {
            sb.append("No enrollment data found for this course.\n");
        } else {
            sb.append("Students enrolled: ").append(ec.getStudents().size()).append("\n");
            sb.append("Teachers assigned: ").append(ec.getTeachers().size()).append("\n");
            sb.append("\n--- Grades ---\n");

            int passed = 0;
            int failed = 0;

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



    private EnrollmentCourse findEnrollment(Course c) {
        for (EnrollmentCourse ec : enrollments) {
            if (ec.getCourse().getId() == c.getId()) return ec;
        }
        return null;
    }

    public List<EnrollmentCourse> getEnrollments() { return enrollments; }
    public List<Schedule> getSchedules() { return schedules; }
    public List<Attendance> getAttendances() { return attendances; }
}