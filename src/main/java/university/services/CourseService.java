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


     // Mark создаётся через EnrollmentCourse

    public Mark putMark(Student s, Course c, double att1, double att2, double finalMark) {
        EnrollmentCourse enrollment = findEnrollment(c);
        if (enrollment == null) {
            enrollment = new EnrollmentCourse(c, null);
            db.getEnrollments().add(enrollment);
        }

        // Проверяем — студент должен быть записан на курс
        if (!enrollment.getStudents().contains(s)) {
            enrollment.addStudent(s);
        }

        Mark mark = new Mark(enrollment, att1, att2, finalMark);
        enrollment.addMark(mark);

        // обновляем GPA студента
        double totalMarks = 0;
        int count = 0;
        for (EnrollmentCourse ec : db.getEnrollments()) {
            if (ec.getStudents().contains(s)) {
                for (Mark m : ec.getMarks()) {
                    totalMarks += m.getTotal();
                    count++;
                }
            }
        }
        if (count > 0) {
            double avgTotal = totalMarks / count;
            double gpa;
            if (avgTotal >= 95)      gpa = 4.0;
            else if (avgTotal >= 90) gpa = 3.67;
            else if (avgTotal >= 85) gpa = 3.33;
            else if (avgTotal >= 80) gpa = 3.0;
            else if (avgTotal >= 75) gpa = 2.67;
            else if (avgTotal >= 70) gpa = 2.33;
            else if (avgTotal >= 65) gpa = 2.0;
            else if (avgTotal >= 60) gpa = 1.67;
            else if (avgTotal >= 55) gpa = 1.33;
            else if (avgTotal >= 50) gpa = 1.0;
            else                     gpa = 0.0;
            s.setGpa(gpa);
        }

// обновляем кредиты
        s.setCredits(s.getCredits() + c.getCredits());

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
        db.getSchedules().add(schedule);
        return schedule;
    }


    // Attendance хранит EnrollmentCourse

    public Attendance markAttendance(Student s, Course c, boolean isPresent) {
        EnrollmentCourse enrollment = findEnrollment(c);
        if (enrollment == null) {
            enrollment = new EnrollmentCourse(c, null);
            db.getEnrollments().add(enrollment);
        }

        Attendance attendance = new Attendance(enrollment, new Date(), isPresent);
        db.getAttendances().add(attendance);
        return attendance;
    }


    // Транскрипт студента — все оценки по всем курсам.
    public String getTranscript(Student s) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("TRANSCRIPT: ").append(s.getFirstName()).append(" ").append(s.getLastName()).append("\n");
        sb.append("GPA: ").append(s.getGpa()).append(" | Credits: ").append(s.getCredits()).append("\n");
        sb.append("========================================\n");

        boolean found = false;
        for (Object obj : db.getEnrollments()) {
            EnrollmentCourse ec = (EnrollmentCourse) obj;
            if (!ec.getStudents().contains(s)) continue;
            for (Mark m : ec.getMarks()) {
                sb.append(String.format("%-25s | ATT1: %5.1f | ATT2: %5.1f | FINAL: %5.1f | TOTAL: %5.1f | %s\n",
                        ec.getCourse().getName(),
                        m.getFirstAttestation(), m.getSecondAttestation(), m.getFinalExam(),
                        m.getTotal(),
                        m.isPassed() ? "PASS" : "FAIL"));
                found = true;
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
                sb.append(String.format("Total: %5.1f | %s\n",
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
        if (!db.getCourses().contains(c)) { // проверка перед добавлением
            db.getCourses().add(c);
        }
        System.out.println("Course approved for registration: " + c.getName());
    }


    // Добавить курс для регистрации (если ещё нет).
    public void addCourseForRegistration(Course c) {
        if (!db.getCourses().contains(c)) {
            db.getCourses().add(c);
            System.out.println("Course added for registration: " + c.getName());
        }
    }


    // Просмотр расписания студента.
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


    // Просмотр расписания по курсу.
    public List<Schedule> getScheduleForCourse(Course c) {
        List<Schedule> result = new ArrayList<>();
        for (Schedule sch : schedules) {
            if (sch.getCourse().getCourseId().equals(c.getCourseId())) {
                result.add(sch);
            }
        }
        return result;
    }

    
    private EnrollmentCourse findEnrollment(Course c) {
        for (Object obj : db.getEnrollments()) {
            EnrollmentCourse ec = (EnrollmentCourse) obj;
            if (ec.getCourse().getCourseId().equals(c.getCourseId())) return ec;
        }
        return null;
    }
//
//    public List<Schedule> getSchedules() { return schedules; }
//    public List<Attendance> getAttendances() { return attendances; }
}