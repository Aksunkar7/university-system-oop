package university.patterns;

import university.enums.*;
import university.interfaces.Researcher;
import university.models.course.Course;
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

        db.getCourses().add(course1);
        db.getCourses().add(course2);
        db.getCourses().add(course3);

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

        // ===== RESEARCH PROJECT =====
        ResearchProject project1 = new ResearchProject("AI in Education");
//        project1.addParticipant((Researcher) teacher1);
        project1.addPaper(paper1);

        db.getResearchProjects().add(project1);

        // ===== STUDENT ORGANIZATION =====
        StudentOrganization org = new StudentOrganization("CS Club", student1);
        org.addMember(student2);

        System.out.println("Data initialized successfully!");
        System.out.println("Users: " + db.getUsers().size());
        System.out.println("Courses: " + db.getCourses().size());
        System.out.println("News: " + db.getNews().size());

        TechSupportService techSupportService = new TechSupportService();
        TechRequest request = techSupportService.sendRequest(teacher1, "Fix projector in room 101");
    }
}
