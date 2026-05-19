package university.patterns;

import university.enums.Language;
import university.enums.ManagerType;
import university.enums.TeacherPosition;
import university.models.users.*;

public class UserFactory {
    
    public static Student createStudent(int id, String firstName, String lastName, String login, String password, int year, String major) {
        return new Student(id, firstName, lastName, login, password, Language.EN, year, major);
    }

    public static Employee createTeacher(int id, String firstName, String lastName, String login, String password, TeacherPosition pos) {
        return new Teacher(id, firstName, lastName, login, password, Language.EN, 0, "", pos);
    }

    public static Employee createManager(int id, String firstName, String lastName, String login, String password, ManagerType managerType) {
        return new Manager(id, firstName, lastName, login, password, Language.EN, 0, "", managerType);
    }

    public static Employee createAdmin(int id, String firstName, String lastName, String login, String password) {
        return new Admin(id, firstName, lastName, login, password, Language.EN, 0, "");
    }
}
