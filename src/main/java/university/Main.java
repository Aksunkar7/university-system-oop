package university;

import university.enums.*;
import university.interfaces.Researcher;
import university.models.course.*;
import university.models.other.*;
import university.models.research.*;
import university.models.users.*;
import university.patterns.DataInitializer;
import university.patterns.DataStorage;
import university.patterns.UserFactory;
import university.services.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static DataStorage db = DataStorage.getInstance();
    static AuthService authService = new AuthService();
    static CourseService courseService = new CourseService();
    static UserService userService = new UserService();
    static NewsService newsService = new NewsService();
    static ResearchService researchService = new ResearchService(db, newsService);
    static TechSupportService techSupportService = new TechSupportService();

    public static void main(String[] args) {
        // Инициализируем тестовые данные
        DataInitializer.initialize();

        System.out.println("================================");
        System.out.println("  UNIVERSITY SYSTEM");
        System.out.println("================================");

        // Главный цикл
        while (true) {
            showMainMenu();
        }
    }

    // ===== ГЛАВНОЕ МЕНЮ =====
    static void showMainMenu() {
        System.out.println("\n1. Login");
        System.out.println("0. Exit");
        System.out.print("Choice: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
            scanner.nextLine();
            return;
        }
        String choice = scanner.nextLine();

        switch (Integer.parseInt(choice)) {
            case 1 -> login();
            case 0 -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice");
        }
    }

    // ===== АВТОРИЗАЦИЯ =====
    static void login() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = authService.login(login, password);

        if (user == null) {
            System.out.println("Invalid login or password!");
            return;
        }

        // Определяем роль и показываем нужное меню
        if (user instanceof Admin) {
            showAdminMenu((Admin) user);
        } else if (user instanceof TechSupportSpecialist) {
            showTechSupportMenu((TechSupportSpecialist) user);
        } else if (user instanceof Manager) {
            showManagerMenu((Manager) user);
        } else if (user instanceof Teacher) {
            showTeacherMenu((Teacher) user);
        } else if (user instanceof GraduateStudent) {
            showGraduateStudentMenu((GraduateStudent) user);
        } else if (user instanceof Student) {
            showStudentMenu((Student) user);
        }
    }

    // ===== STUDENT МЕНЮ =====
    static void showStudentMenu(Student student) {
        while (true) {
            System.out.println("\n=== STUDENT MENU ===");
            System.out.println("Welcome, " + student.getFirstName() + "!");
            System.out.println("GPA: " + student.getGpa() + " | Credits: " + student.getCredits());
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. View My Marks");
            System.out.println("4. Get Transcript");
            System.out.println("5. View Attendance");
            System.out.println("6. View Schedule");
            System.out.println("7. View News");
            System.out.println("8. Rate Teacher");
            System.out.println("9. Join Student Organization");
            System.out.println("10. View My Organizations");
            System.out.println("11. Subscribe to Journal");
            System.out.println("12. View My Journals");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("\n=== AVAILABLE COURSES ===");
                    for (Course c : db.getCourses()) {
                        System.out.println(c);
                    }
                }
                case 2 -> {
                    System.out.println("\n=== AVAILABLE COURSES ===");
                    List<Course> courses = db.getCourses();
                    for (int i = 0; i < courses.size(); i++) {
                        System.out.println((i + 1) + ". " + courses.get(i));
                    }
                    System.out.print("Select course number: ");
                    int courseIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if (courseIndex >= 0 && courseIndex < courses.size()) {
                        try {
                            EnrollmentCourse enrollment = courseService
                                    .registerStudentToCourse(student, courses.get(courseIndex));
                            System.out.println("Successfully registered for: " +
                                    courses.get(courseIndex).getName());
                            db.log(student.getFirstName() + " registered for " +
                                    courses.get(courseIndex).getName());
                            student.setCredits(student.getCredits() + courses.get(courseIndex).getCredits());
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                }
                case 3 -> {
                    System.out.println("\n=== MY MARKS ===");
                    boolean found = false;
                    for (EnrollmentCourse ec : db.getEnrollments()) {
                        if (ec.getStudents().contains(student)) {
                            for (Mark m : ec.getMarks()) {
                                System.out.println(m);
                                found = true;
                            }
                        }
                    }
                    if (!found) System.out.println("No marks yet");
                }
                case 4 -> {
                    System.out.println(courseService.getTranscript(student));
                }
                case 5 -> {
                    System.out.println("\n=== MY ATTENDANCE ===");
                    boolean found = false;
                    for (Attendance a : db.getAttendances()) {
                        if (a.getEnrollment().getStudents().contains(student)) {
                            System.out.println(a);
                            found = true;
                        }
                    }
                    if (!found) System.out.println("No attendance records yet");
                }
                case 6 -> {
                    System.out.println("\n=== SCHEDULE ===");
                    for (Schedule s : db.getSchedules()) {
                        System.out.println(s);
                    }
                }
                case 7 -> {
                    System.out.println("\n=== NEWS ===");
                    // Pinned news first
                    for (News n : db.getNews()) {
                        if (n.isPinned()) System.out.println("[PINNED] " + n);
                    }
                    for (News n : db.getNews()) {
                        if (!n.isPinned()) System.out.println(n);
                    }
                }
                case 8 -> {
                    System.out.println("Select Teacher:");
                    List<User> users = db.getUsers();
                    int count = 1;
                    for (User u : users) {
                        if (u instanceof Teacher) {
                            System.out.println(count++ + ". " + u.getInfo());
                        }
                    }
                    System.out.print("Teacher number: ");
                    int tNum = scanner.nextInt() - 1;
                    scanner.nextLine();

                    List<Teacher> teachers = new ArrayList<>();
                    for (User u : db.getUsers()) {
                        if (u instanceof Teacher) teachers.add((Teacher) u);
                    }

                    System.out.print("Rating (1-5): ");
                    double rating = scanner.nextDouble();
                    scanner.nextLine();

                    courseService.rateTeacher(student, teachers.get(tNum), rating);
                }
                case 9 -> {
                    System.out.println("\n=== STUDENT ORGANIZATIONS ===");
                    List<StudentOrganization> orgs = db.getOrganizations();
                    if (orgs.isEmpty()) {
                        System.out.println("No organizations available");
                        break;
                    }
                    for (int i = 0; i < orgs.size(); i++) {
                        System.out.println((i + 1) + ". " + orgs.get(i));
                    }
                    System.out.print("Select organization: ");
                    int orgIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    orgs.get(orgIndex).addMember(student);
                    System.out.println("Joined: " + orgs.get(orgIndex).getName());
                    db.log(student.getFirstName() + " joined " +
                            orgs.get(orgIndex).getName());
                }
                case 10 -> {
                    System.out.println("\n=== MY ORGANIZATIONS ===");
                    boolean found = false;
                    for (StudentOrganization org : db.getOrganizations()) {
                        if (org.getMembers().contains(student)) {
                            System.out.println(org);
                            found = true;
                        }
                    }
                    if (!found) System.out.println("You are not in any organization");
                }
                case 11 -> {
                    System.out.println("\n=== AVAILABLE JOURNALS ===");
                    List<Journal> journals = db.getJournals();
                    if (journals.isEmpty()) {
                        System.out.println("No journals available");
                        break;
                    }
                    for (int i = 0; i < journals.size(); i++) {
                        System.out.println((i + 1) + ". " + journals.get(i));
                    }
                    System.out.print("Select journal: ");
                    int jIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    newsService.subscribeToJournal(student, journals.get(jIndex));
                    System.out.println("Subscribed to: " + journals.get(jIndex).getName());
                }
                case 12 -> {
                    System.out.println("\n=== MY JOURNALS ===");
                    boolean found = false;
                    for (Journal j : db.getJournals()) {
                        if (j.getSubscribers().contains(student)) {
                            System.out.println(j);
                            found = true;
                        }
                    }
                    if (!found) System.out.println("No subscriptions yet");
                }
                case 0 -> {
                    System.out.println("Logged out!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ===== TEACHER МЕНЮ =====
    static void showTeacherMenu(Teacher teacher) {
        while (true) {
            System.out.println("\n=== TEACHER MENU ===");
            System.out.println("Welcome, " + teacher.getFirstName() + "!");
            System.out.println("1. View My Courses");
            System.out.println("2. Put Mark");
            System.out.println("3. View Students");
            System.out.println("4. Mark Attendance");
            System.out.println("5. Generate Report");
            System.out.println("6. Send Complaint");
            System.out.println("7. Send Message");
            System.out.println("8. View My Messages");
            System.out.println("9. Subscribe to Journal");
            System.out.println("10. View My Journals");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("\n=== MY COURSES ===");
                    for (Course c : teacher.getCourses()) {
                        System.out.println(c);
                    }
                    if (teacher.getCourses().isEmpty())
                        System.out.println("No courses assigned yet");
                }
                case 2 -> {
                    List<EnrollmentCourse> enrollments = db.getEnrollments();
                    if (enrollments.isEmpty()) {
                        System.out.println("No enrollments found");
                        break;
                    }
                    System.out.println("\n=== SELECT ENROLLMENT ===");
                    for (int i = 0; i < enrollments.size(); i++) {
                        System.out.println((i + 1) + ". " + enrollments.get(i));
                    }
                    System.out.print("Select enrollment: ");
                    int ecIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (ecIndex >= 0 && ecIndex < enrollments.size()) {
                        EnrollmentCourse ec = enrollments.get(ecIndex);
                        System.out.println("Students in this enrollment:");
                        for (int i = 0; i < ec.getStudents().size(); i++) {
                            System.out.println((i + 1) + ". " + ec.getStudents().get(i));
                        }
                        System.out.print("Select student: ");
                        int sIndex = scanner.nextInt() - 1;
                        scanner.nextLine();

                        if (sIndex >= 0 && sIndex < ec.getStudents().size()) {
                            Student s = ec.getStudents().get(sIndex);

                            // ATT1 валидация
                            System.out.print("ATT1 (0-30): ");
                            double att1 = scanner.nextDouble();
                            while (att1 < 0 || att1 > 30) {
                                System.out.print("Invalid! ATT1 must be 0-30: ");
                                att1 = scanner.nextDouble();
                            }

                            // ATT2 валидация
                            System.out.print("ATT2 (0-30): ");
                            double att2 = scanner.nextDouble();
                            while (att2 < 0 || att2 > 30) {
                                System.out.print("Invalid! ATT2 must be 0-30: ");
                                att2 = scanner.nextDouble();
                            }

                            // Проверка допуска к финалу
                            if (att1 + att2 <= 29.5) {
                                System.out.println("Student NOT ADMITTED to final exam!");
                                System.out.println("ATT1 + ATT2 = " + (att1 + att2) + " <= 29.5");
                                Mark mark = courseService.putMark(s, ec.getCourse(), att1, att2, 0);
                                System.out.println("Mark added: " + mark);
                                db.log(teacher.getFirstName() + " put mark for " + s.getFirstName() + " - NOT ADMITTED");
                                break;
                            }

                            // FINAL валидация
                            System.out.print("FINAL (0-40): ");
                            double finalMark = scanner.nextDouble();
                            while (finalMark < 0 || finalMark > 40) {
                                System.out.print("Invalid! FINAL must be 0-40: ");
                                finalMark = scanner.nextDouble();
                            }
                            scanner.nextLine();

                            Mark mark = courseService.putMark(s, ec.getCourse(), att1, att2, finalMark);
                            System.out.println("Mark added: " + mark);

                            // Показываем статус
                            if (finalMark < 9.5) {
                                System.out.println("Status: RETAKE — final exam score too low!");
                            } else if (finalMark < 19.5) {
                                System.out.println("Status: FX — need to retake final!");
                            }

                            db.log(teacher.getFirstName() + " put mark for " + s.getFirstName());
                        }
                    }
                }
                case 3 -> {
                    System.out.println("\n=== STUDENTS ===");
                    for (EnrollmentCourse ec : db.getEnrollments()) {
                        System.out.println("Course: " + ec.getCourse().getName());
                        for (Student s : ec.getStudents()) {
                            System.out.println("  - " + s.getInfo());
                        }
                    }
                }
                case 4 -> {
                    List<EnrollmentCourse> enrollments = db.getEnrollments();
                    if (enrollments.isEmpty()) {
                        System.out.println("No enrollments found");
                        break;
                    }
                    System.out.println("Select enrollment:");
                    for (int i = 0; i < enrollments.size(); i++) {
                        System.out.println((i + 1) + ". " + enrollments.get(i));
                    }
                    int ecIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    EnrollmentCourse ec = enrollments.get(ecIndex);
                    for (int i = 0; i < ec.getStudents().size(); i++) {
                        System.out.println((i + 1) + ". " + ec.getStudents().get(i));
                    }
                    System.out.print("Select student: ");
                    int sIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    System.out.print("Present? (true/false): ");
                    boolean present = scanner.nextBoolean();
                    scanner.nextLine();

                    Attendance att = courseService.markAttendance(
                            ec.getStudents().get(sIndex), ec.getCourse(), present);
                    System.out.println("Attendance marked: " + att);
                }
                case 5 -> {
                    System.out.println("Select course:");
                    List<Course> courses = db.getCourses();
                    for (int i = 0; i < courses.size(); i++) {
                        System.out.println((i + 1) + ". " + courses.get(i));
                    }
                    int cIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    System.out.println(courseService.generateReport(teacher, courses.get(cIndex)));
                }
                case 6 -> {
                    System.out.println("Students:");
                    List<User> users = db.getUsers();
                    int count = 1;
                    for (User u : users) {
                        if (u instanceof Student) {
                            System.out.println(count++ + ". " + u.getInfo());
                        }
                    }
                    System.out.print("Select student number: ");
                    int sNum = scanner.nextInt() - 1;
                    scanner.nextLine();

                    System.out.println("Urgency (LOW/MEDIUM/HIGH): ");
                    System.out.println("1. LOW  2. MEDIUM  3. HIGH");
                    int urgencyChoice = scanner.nextInt();
                    scanner.nextLine();

                    UrgencyLevel urgency = switch (urgencyChoice) {
                        case 1 -> UrgencyLevel.LOW;
                        case 2 -> UrgencyLevel.MEDIUM;
                        default -> UrgencyLevel.HIGH;
                    };

                    System.out.println("Complaint sent with urgency: " + urgency);
                    db.log(teacher.getFirstName() + " sent complaint with urgency " + urgency);
                    db.getComplaints().add(teacher.getFirstName() +
                            " sent complaint with urgency: " + urgency);
                }
                case 7 -> {
                    System.out.println("Select recipient:");
                    List<User> users = db.getUsers();
                    int count = 1;
                    for (User u : users) {
                        if (u instanceof Employee && !u.equals(teacher)) {
                            System.out.println(count++ + ". " + u.getInfo());
                        }
                    }
                    System.out.print("Recipient number: ");
                    int rNum = scanner.nextInt() - 1;
                    scanner.nextLine();

                    List<Employee> employees = new ArrayList<>();
                    for (User u : db.getUsers()) {
                        if (u instanceof Employee && !u.equals(teacher)) {
                            employees.add((Employee) u);
                        }
                    }

                    System.out.print("Message: ");
                    String content = scanner.nextLine();
                    userService.sendMessage(teacher, employees.get(rNum), content);
                }
                case 8 -> {
                    System.out.println("\n=== MY MESSAGES ===");
                    List<university.models.other.Message> messages =
                            userService.getMessagesForEmployee(teacher);
                    if (messages.isEmpty()) {
                        System.out.println("No messages");
                    } else {
                        messages.forEach(System.out::println);
                    }
                }
                case 9 -> {
                    System.out.println("\n=== AVAILABLE JOURNALS ===");
                    List<Journal> journals = db.getJournals();
                    if (journals.isEmpty()) {
                        System.out.println("No journals available");
                        break;
                    }
                    for (int i = 0; i < journals.size(); i++) {
                        System.out.println((i + 1) + ". " + journals.get(i));
                    }
                    System.out.print("Select journal: ");
                    int jIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    newsService.subscribeToJournal(teacher, journals.get(jIndex));
                    System.out.println("Subscribed to: " + journals.get(jIndex).getName());
                }
                case 10 -> {
                    System.out.println("\n=== MY JOURNALS ===");
                    boolean found = false;
                    for (Journal j : db.getJournals()) {
                        if (j.getSubscribers().contains(teacher)) {
                            System.out.println(j);
                            found = true;
                        }
                    }
                    if (!found) System.out.println("No subscriptions yet");
                }
                case 0 -> {
                    System.out.println("Logged out!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ===== MANAGER МЕНЮ =====
    static void showManagerMenu(Manager manager) {
        while (true) {
            System.out.println("\n=== MANAGER MENU ===");
            System.out.println("Welcome, " + manager.getFirstName() + "!");
            System.out.println("1. Assign Course to Teacher");
            System.out.println("2. Approve Course Registration");
            System.out.println("3. View All Students");
            System.out.println("4. View All Teachers");
            System.out.println("5. View News");
            System.out.println("6. Generate Schedule");
            System.out.println("7. Get Top Cited Researcher");
            System.out.println("8. View Employee Requests");
            System.out.println("9. View Complaints");
            System.out.println("10. Create New Course");
            System.out.println("11. Send Message");
            System.out.println("12. View My Messages");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Select Teacher:");
                    List<User> users = db.getUsers();
                    int count = 1;
                    for (User u : users) {
                        if (u instanceof Teacher) {
                            System.out.println(count++ + ". " + u.getInfo());
                        }
                    }
                    System.out.print("Teacher number: ");
                    int tNum = scanner.nextInt() - 1;
                    scanner.nextLine();

                    System.out.println("Select Course:");
                    List<Course> courses = db.getCourses();
                    for (int i = 0; i < courses.size(); i++) {
                        System.out.println((i + 1) + ". " + courses.get(i));
                    }
                    System.out.print("Course number: ");
                    int cNum = scanner.nextInt() - 1;
                    scanner.nextLine();

                    Teacher teacher = (Teacher) users.stream()
                            .filter(u -> u instanceof Teacher)
                            .toList().get(tNum);
                    courseService.assignTeacherToCourse(teacher, courses.get(cNum));
                    System.out.println("Course assigned successfully!");
                    db.log(manager.getFirstName() + " assigned " + teacher.getFirstName() +
                            " to " + courses.get(cNum).getName());
                }
                case 2 -> {
                    System.out.println("Select Course to Approve:");
                    List<Course> courses = db.getCourses();
                    for (int i = 0; i < courses.size(); i++) {
                        System.out.println((i + 1) + ". " + courses.get(i));
                    }
                    System.out.print("Course number: ");
                    int cNum = scanner.nextInt() - 1;
                    scanner.nextLine();
                    courseService.approveCourseRegistration(courses.get(cNum));
                    System.out.println("Course approved: " + courses.get(cNum).getName());
                }
                case 3 -> {
                    System.out.println("\n=== ALL STUDENTS ===");
                    userService.getAllStudents().forEach(s -> System.out.println(s.getInfo()));
                }
                case 4 -> {
                    System.out.println("\n=== ALL TEACHERS ===");
                    userService.getAllTeachers().forEach(t -> System.out.println(t.getInfo()));
                }
                case 5 -> {
                    System.out.println("\n=== NEWS ===");
                    for (News n : db.getNews()) {
                        if (n.isPinned()) System.out.println("[PINNED] " + n);
                    }
                    for (News n : db.getNews()) {
                        if (!n.isPinned()) System.out.println(n);
                    }
                }
                case 6 -> {
                    System.out.println("Select Course:");
                    List<Course> courses = db.getCourses();
                    for (int i = 0; i < courses.size(); i++) {
                        System.out.println((i + 1) + ". " + courses.get(i));
                    }
                    System.out.print("Course number: ");
                    int cNum = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (!db.getCourses().isEmpty()) {
                        Room room = new Room("101", 30, RoomType.LECTURE_HALL);
                        Schedule schedule = courseService.generateSchedule(
                                courses.get(cNum), room);
//                        db.getSchedules().add(schedule);
                        System.out.println("Schedule generated: " + schedule);
                    }
                }
                case 7 -> {
                    Researcher top = researchService.getTopCitedResearcher();
                    if (top != null) {
                        System.out.println("Top cited researcher: " + ((User) top).getInfo());
                        System.out.println("H-Index: " + researchService.calculateHIndex(top));
                    } else {
                        System.out.println("No researchers found");
                    }
                }
                case 8 -> {
                    System.out.println("\n=== EMPLOYEE REQUESTS ===");
                    if (db.getTechRequests().isEmpty()) {
                        System.out.println("No requests");
                    } else {
                        db.getTechRequests().forEach(System.out::println);
                    }
                }
                case 9 -> {
                    System.out.println("\n=== COMPLAINTS ===");
                    if (db.getComplaints().isEmpty()) {
                        System.out.println("No complaints");
                    } else {
                        db.getComplaints().forEach(System.out::println);
                    }
                }
                case 10 -> {
                    System.out.print("Course ID: ");
                    String courseId = scanner.nextLine();
                    System.out.print("Course Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Credits: ");
                    int credits = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Type: 1.MAJOR  2.MINOR  3.FREE_ELECTIVE");
                    int typeChoice = scanner.nextInt();
                    scanner.nextLine();

                    CourseType type = switch (typeChoice) {
                        case 1 -> CourseType.MAJOR;
                        case 2 -> CourseType.MINOR;
                        default -> CourseType.FREE_ELECTIVE;
                    };

                    Course newCourse = courseService.createCourse(courseId, name, credits, type);
                    System.out.println("Course created: " + newCourse);
                    db.log(manager.getFirstName() + " created course: " + name);
                }
                case 11 -> {
                    System.out.println("Select recipient:");
                    List<User> users = db.getUsers();
                    int count = 1;
                    for (User u : users) {
                        if (u instanceof Employee && !u.equals(manager)) {
                            System.out.println(count++ + ". " + u.getInfo());
                        }
                    }
                    System.out.print("Recipient number: ");
                    int rNum = scanner.nextInt() - 1;
                    scanner.nextLine();

                    List<Employee> employees = new ArrayList<>();
                    for (User u : db.getUsers()) {
                        if (u instanceof Employee && !u.equals(manager)) {
                            employees.add((Employee) u);
                        }
                    }

                    System.out.print("Message: ");
                    String content = scanner.nextLine();
                    userService.sendMessage(manager, employees.get(rNum), content);
                }
                case 12 -> {
                    System.out.println("\n=== MY MESSAGES ===");
                    List<university.models.other.Message> messages =
                            userService.getMessagesForEmployee(manager);
                    if (messages.isEmpty()) {
                        System.out.println("No messages");
                    } else {
                        messages.forEach(System.out::println);
                    }
                }
                case 0 -> {
                    System.out.println("Logged out!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ===== ADMIN МЕНЮ =====
    static void showAdminMenu(Admin admin) {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("Welcome, " + admin.getFirstName() + "!");
            System.out.println("1. View All Users");
            System.out.println("2. Add User");
            System.out.println("3. Remove User");
            System.out.println("4. View Logs");
            System.out.println("5. Send Message");
            System.out.println("6. View My Messages");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("\n=== ALL USERS ===");
                    db.getUsers().forEach(u -> System.out.println(u.getInfo()));
                }
                case 2 -> {
                    System.out.println("Enter user details:");
                    System.out.print("First name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Login: ");
                    String login = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.println("Role: 1.Student 2.Teacher");
                    int roleChoice = scanner.nextInt();
                    scanner.nextLine();

                    User newUser;
                    int newId = db.getUsers().size() + 1;
                    if (roleChoice == 1) {
                        newUser = UserFactory.createStudent(newId, firstName,
                                lastName, login, password, 1, "CS");
                    } else {
                        newUser = UserFactory.createTeacher(newId, firstName, lastName,
                                login, password, TeacherPosition.LECTOR);
                    }
                    userService.addUser(newUser);
                    System.out.println("User added: " + newUser.getInfo());
                }
                case 3 -> {
                    System.out.println("Enter user ID to remove: ");
                    db.getUsers().forEach(u -> System.out.println(u.getId() + ". " + u.getInfo()));
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    userService.removeUser(id);
                    System.out.println("User removed");
                }
                case 4 -> {
                    System.out.println("\n=== LOGS ===");
                    if (db.getLogs().isEmpty()) {
                        System.out.println("No logs yet");
                    } else {
                        db.getLogs().forEach(System.out::println);
                    }
                }
                case 5 -> {
                    System.out.println("Select recipient:");
                    List<User> users = db.getUsers();
                    int count = 1;
                    for (User u : users) {
                        if (u instanceof Employee && !u.equals(admin)) {
                            System.out.println(count++ + ". " + u.getInfo());
                        }
                    }
                    System.out.print("Recipient number: ");
                    int rNum = scanner.nextInt() - 1;
                    scanner.nextLine();

                    List<Employee> employees = new ArrayList<>();
                    for (User u : db.getUsers()) {
                        if (u instanceof Employee && !u.equals(admin)) {
                            employees.add((Employee) u);
                        }
                    }

                    System.out.print("Message: ");
                    String content = scanner.nextLine();
                    userService.sendMessage(admin, employees.get(rNum), content);
                }
                case 6 -> {
                    System.out.println("\n=== MY MESSAGES ===");
                    List<Message> messages = userService.getMessagesForEmployee(admin);
                    if (messages.isEmpty()) {
                        System.out.println("No messages");
                    } else {
                        messages.forEach(System.out::println);
                    }
                }
                case 0 -> {
                    System.out.println("Logged out!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ===== TECHSUPPORT МЕНЮ =====
    static void showTechSupportMenu(TechSupportSpecialist tech) {
        while (true) {
            System.out.println("\n=== TECH SUPPORT MENU ===");
            System.out.println("Welcome, " + tech.getFirstName() + "!");
            System.out.println("1. View Requests");
            System.out.println("2. Accept Request");
            System.out.println("3. Reject Request");
            System.out.println("4. Send Message");
            System.out.println("5. View My Messages");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("\n=== REQUESTS ===");
                    List<TechRequest> requests = techSupportService.viewRequests();
                    if (requests.isEmpty()) {
                        System.out.println("No requests");
                    } else {
                        requests.forEach(System.out::println);
                    }
                }
                case 2 -> {
                    List<TechRequest> requests = db.getTechRequests();
                    if (requests.isEmpty()) {
                        System.out.println("No requests");
                        break;
                    }
                    for (int i = 0; i < requests.size(); i++) {
                        System.out.println((i + 1) + ". " + requests.get(i));
                    }
                    System.out.print("Select request: ");
                    int rIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    techSupportService.acceptRequest(requests.get(rIndex));
                    System.out.println("Request accepted!");
                }
                case 3 -> {
                    List<TechRequest> requests = db.getTechRequests();
                    if (requests.isEmpty()) {
                        System.out.println("No requests");
                        break;
                    }
                    for (int i = 0; i < requests.size(); i++) {
                        System.out.println((i + 1) + ". " + requests.get(i));
                    }
                    System.out.print("Select request: ");
                    int rIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    techSupportService.rejectRequest(requests.get(rIndex));
                    System.out.println("Request rejected!");
                }
                case 4 -> {
                    System.out.println("Select recipient:");
                    List<User> users = db.getUsers();
                    int count = 1;
                    for (User u : users) {
                        if (u instanceof Employee && !u.equals(tech)) {
                            System.out.println(count++ + ". " + u.getInfo());
                        }
                    }
                    System.out.print("Recipient number: ");
                    int rNum = scanner.nextInt() - 1;
                    scanner.nextLine();

                    List<Employee> employees = new ArrayList<>();
                    for (User u : db.getUsers()) {
                        if (u instanceof Employee && !u.equals(tech)) {
                            employees.add((Employee) u);
                        }
                    }

                    System.out.print("Message: ");
                    String content = scanner.nextLine();
                    userService.sendMessage(tech, employees.get(rNum), content);
                }
                case 5 -> {
                    System.out.println("\n=== MY MESSAGES ===");
                    List<university.models.other.Message> messages =
                            userService.getMessagesForEmployee(tech);
                    if (messages.isEmpty()) {
                        System.out.println("No messages");
                    } else {
                        messages.forEach(System.out::println);
                    }
                }
                case 0 -> {
                    System.out.println("Logged out!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ===== GRADUATE STUDENT МЕНЮ =====
    static void showGraduateStudentMenu(GraduateStudent gradStudent) {
        while (true) {
            System.out.println("\n=== GRADUATE STUDENT MENU ===");
            System.out.println("Welcome, " + gradStudent.getFirstName() + "!");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. View My Marks");
            System.out.println("4. Get Transcript");
            System.out.println("5. Add Research Paper");
            System.out.println("6. View My Papers");
            System.out.println("7. Calculate H-Index");
            System.out.println("8. Subscribe to Journal");
            System.out.println("9. View My Journals");
            System.out.println("10. Publish Paper in Journal");
            System.out.println("11. Print Papers (sorted)");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("\n=== AVAILABLE COURSES ===");
                    db.getCourses().forEach(System.out::println);
                }
                case 2 -> {
                    List<Course> courses = db.getCourses();
                    for (int i = 0; i < courses.size(); i++) {
                        System.out.println((i + 1) + ". " + courses.get(i));
                    }
                    System.out.print("Select course: ");
                    int cIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    try {
                        courseService.registerStudentToCourse(gradStudent, courses.get(cIndex));
                        System.out.println("Registered successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("\n=== MY MARKS ===");
                    boolean found = false;
                    for (EnrollmentCourse ec : db.getEnrollments()) {
                        if (ec.getStudents().contains(gradStudent)) {
                            for (Mark m : ec.getMarks()) {
                                System.out.println(m);
                                found = true;
                            }
                        }
                    }
                    if (!found) System.out.println("No marks yet");
                }
                case 4 -> System.out.println(courseService.getTranscript(gradStudent));
                case 5 -> {
                    System.out.print("Paper title: ");
                    String title = scanner.nextLine();
                    System.out.print("Journal: ");
                    String journal = scanner.nextLine();
                    ResearchPaper paper = new ResearchPaper(title);
                    paper.setJournal(journal);
                    researchService.publishPaper(gradStudent, paper);
                    System.out.println("Paper published!");
                }
                case 6 -> {
                    System.out.println("\n=== MY PAPERS ===");
                    gradStudent.getPapers().forEach(System.out::println);
                }
                case 7 -> {
                    int hIndex = researchService.calculateHIndex(gradStudent);
                    System.out.println("Your H-Index: " + hIndex);
                }
                case 8 -> {
                    System.out.println("\n=== AVAILABLE JOURNALS ===");
                    List<Journal> journals = db.getJournals();
                    if (journals.isEmpty()) {
                        System.out.println("No journals available");
                        break;
                    }
                    for (int i = 0; i < journals.size(); i++) {
                        System.out.println((i + 1) + ". " + journals.get(i));
                    }
                    System.out.print("Select journal: ");
                    int jIndex = scanner.nextInt() - 1;
                    scanner.nextLine();
                    newsService.subscribeToJournal(gradStudent, journals.get(jIndex));
                    System.out.println("Subscribed to: " + journals.get(jIndex).getName());
                }
                case 9 -> {
                    System.out.println("\n=== MY JOURNALS ===");
                    boolean found = false;
                    for (Journal j : db.getJournals()) {
                        if (j.getSubscribers().contains(gradStudent)) {
                            System.out.println(j);
                            found = true;
                        }
                    }
                    if (!found) System.out.println("No subscriptions yet");
                }
                case 10 -> {
                    List<ResearchPaper> papers = gradStudent.getPapers();
                    if (papers.isEmpty()) {
                        System.out.println("No papers yet!");
                        break;
                    }
                    System.out.println("Select paper:");
                    for (int i = 0; i < papers.size(); i++) {
                        System.out.println((i + 1) + ". " + papers.get(i));
                    }
                    System.out.print("Paper: ");
                    int pIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    System.out.println("Select journal:");
                    List<Journal> journals = db.getJournals();
                    for (int i = 0; i < journals.size(); i++) {
                        System.out.println((i + 1) + ". " + journals.get(i));
                    }
                    System.out.print("Journal: ");
                    int jIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    newsService.publishPaperInJournal(
                            journals.get(jIndex), papers.get(pIndex));
                    System.out.println("Published! Subscribers notified! (Observer)");
                }
                case 11 -> {
                    System.out.println("Sort by:");
                    System.out.println("1. Citations");
                    System.out.println("2. Date");
                    System.out.println("3. Pages");
                    System.out.print("Choice: ");
                    int sortChoice = scanner.nextInt();
                    scanner.nextLine();

                    Comparator<ResearchPaper> comparator = switch (sortChoice) {
                        case 1 -> Comparator.comparingInt(ResearchPaper::getCitations).reversed();
                        case 2 -> Comparator.comparing(ResearchPaper::getDate).reversed();
                        default -> Comparator.comparingInt(ResearchPaper::getPages).reversed();
                    };

                    System.out.println("\n=== MY PAPERS (sorted) ===");
                    researchService.printPapers(gradStudent, comparator);
                    System.out.println("(Strategy pattern - sorted by your choice)");
                }
                case 0 -> {
                    System.out.println("Logged out!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
