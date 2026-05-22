package university.patterns;

import university.enums.*;
import university.interfaces.Researcher;
import university.models.course.*;
import university.models.other.News;
import university.models.other.StudentOrganization;
import university.models.other.TechRequest;
import university.models.research.Journal;
import university.models.research.ResearchPaper;
import university.models.research.ResearchProject;
import university.models.users.*;
import university.services.TechSupportService;

import java.util.Arrays;
import java.util.Date;

public class DataInitializer {

    public static void initialize() {
        DataStorage db = DataStorage.getInstance();

        // ===== USERS =====

        // Admin
        Admin admin = new Admin(1, "Aibek", "Adminov",
                "admin", "admin123",
                Language.EN, 500000, "IT");

        // Teachers
        Teacher teacher1 = new Teacher(2, "Assylzhan", "Izbassar",
                "teacher1", "pass123",
                Language.EN, 300000, "CS",
                TeacherPosition.PROFESSOR);

        Teacher teacher2 = new Teacher(3, "Pakizar", "Shamoi",
                "teacher2", "pass123",
                Language.EN, 280000, "CS",
                TeacherPosition.SENIOR_LECTOR);

        // Manager
        Manager manager = new Manager(4, "Nurlan", "Managerov",
                "manager", "pass123",
                Language.EN, 400000, "OR",
                ManagerType.OR);

        // Students
        Student student1 = new Student(5, "Aksungkar", "Bekzhanov",
                "student1", "pass123",
                Language.EN, 2, "CS");

        Student student2 = new Student(6, "Aibek", "Studentov",
                "student2", "pass123",
                Language.EN, 1, "IT");

        // GraduateStudent
        GraduateStudent gradStudent = new GraduateStudent(7, "Nurbol", "Gradov",
                "grad1", "pass123",
                Language.EN, 1, "CS",
                DegreeType.MASTER);

        // TechSupport
        TechSupportSpecialist techSupport = new TechSupportSpecialist(8, "Dias", "Techov",
                "tech1", "pass123",
                Language.EN, 200000, "IT");

        // Добавляем всех пользователей в DataStorage
        db.getUsers().add(admin);
        db.getUsers().add(teacher1);
        db.getUsers().add(teacher2);
        db.getUsers().add(manager);
        db.getUsers().add(student1);
        db.getUsers().add(student2);
        db.getUsers().add(gradStudent);
        db.getUsers().add(techSupport);

        // ===== COURSES =====
        Course course1 = new Course("1", "OOP and Design", 5, CourseType.MAJOR, 3, 2);
        Course course2 = new Course("2", "Data Structures", 4, CourseType.MAJOR, 2, 2);
        Course course3 = new Course("3", "English", 2, CourseType.FREE_ELECTIVE, 1, 1);
        Course course4 = new Course("4", "Web Dev", 16, CourseType.MAJOR, 13, 3);

        db.getCourses().add(course1);
        db.getCourses().add(course2);
        db.getCourses().add(course3);
        db.getCourses().add(course4);

        // ===== NEWS =====
        News news1 = new News("Welcome to university!",
                "New semester has started", "General");

        News news2 = new News("Research conference",
                "Annual research conference next week", "Research");
        news2.pin(); // Research news всегда pinned

        db.getNews().add(news1);
        db.getNews().add(news2);

        // ===== JOURNALS =====
        Journal journal1 = new Journal("Computer Science Journal");
        Journal journal2 = new Journal("Research Weekly");

        db.getJournals().add(journal1);
        db.getJournals().add(journal2);

        // ===== RESEARCH PAPERS =====
        ResearchPaper paper1 = new ResearchPaper(
                "Machine Learning in Education",
                Arrays.asList("Izbassar A.", "Shamoi P."),
                "CS Journal", new Date(), "10.1234/cs.2024", 15);
        paper1.setCitations(10);

        ResearchPaper paper2 = new ResearchPaper(
                "OOP Best Practices",
                Arrays.asList("Izbassar A."),
                "Software Engineering Journal", new Date(), "10.1234/se.2024", 8);
        paper2.setCitations(5);

        db.getResearchPapers().add(paper1);
        db.getResearchPapers().add(paper2);
        teacher1.addPaper(paper1);
        teacher1.addPaper(paper2);

        // ===== RESEARCH PROJECT =====
        ResearchProject project1 = new ResearchProject("AI in Education");
        project1.addParticipant((Researcher) teacher1);
        project1.addPaper(paper1);

        db.getResearchProjects().add(project1);

        // ===== STUDENT ORGANIZATION =====
        StudentOrganization org = new StudentOrganization("CS Club", student1);
        org.addMember(student2);
        db.getOrganizations().add(org);

        System.out.println("Data initialized successfully!");
        System.out.println("Users: " + db.getUsers().size());
        System.out.println("Courses: " + db.getCourses().size());
        System.out.println("News: " + db.getNews().size());

        TechSupportService techSupportService = new TechSupportService();
        TechRequest request = techSupportService.sendRequest(teacher1, "Fix projector in room 101");

        // В конце initialize() добавь:

// ===== PRE-ASSIGN COURSES =====
// Назначаем учителей на курсы
        EnrollmentCourse ec1 = new EnrollmentCourse(course1, LessonType.LECTURE);
        ec1.addTeacher(teacher1);
        db.getEnrollments().add(ec1);

        EnrollmentCourse ec2 = new EnrollmentCourse(course2, LessonType.PRACTICE);
        ec2.addTeacher(teacher2);
        db.getEnrollments().add(ec2);

// ===== PRE-REGISTER STUDENTS =====
// Регистрируем студентов на курсы
        ec1.addStudent(student1);
        ec2.addStudent(student2);
        student1.setCredits(student1.getCredits() + course1.getCredits());
        student2.setCredits(student2.getCredits() + course2.getCredits());

// ===== PRE-SET MARKS =====
// Ставим оценки
        Mark mark1 = new Mark(ec1, 28, 27, 38);
        ec1.addMark(mark1);
        student1.setGpa(3.67); // 93 total → A-

        Mark mark2 = new Mark(ec2, 20, 22, 30);
        ec2.addMark(mark2);
        student2.setGpa(2.0); // 72 total → C+

// ===== PRE-SCHEDULE =====
        Room room1 = new Room("101", 60, RoomType.LECTURE_HALL);
        Room room2 = new Room("205", 30, RoomType.LAB);
        Schedule schedule1 = new Schedule(course1, teacher1, room1, new Date());
        Schedule schedule2 = new Schedule(course2, teacher2, room2, new Date());
        db.getSchedules().add(schedule1);
        db.getSchedules().add(schedule2);

// ===== PRE-ATTENDANCE =====
        Attendance att1 = new Attendance(ec1, new Date(), true);
        Attendance att2 = new Attendance(ec1, new Date(), false);
        db.getAttendances().add(att1);
        db.getAttendances().add(att2);

// ===== PRE-TECH REQUESTS =====
        TechSupportService tss = new TechSupportService();
        tss.sendRequest(teacher2, "Replace keyboard in lab 205");

// ===== PRE-NEWS =====
        News news3 = new News("Top Researcher Award",
                "Prof. Izbassar received top researcher award", "Research");
        news3.pin();
        db.getNews().add(news3);
    }
}
