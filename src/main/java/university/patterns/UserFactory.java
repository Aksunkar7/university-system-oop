package university.patterns;

import university.enums.Language;
import university.models.users.*;

public class UserFactory {
    
    public static Student createStudent(int id, String firstName, String lastName, String login, String password, int year, String major) {
        return new Student(id, firstName, lastName, login, password, Language.EN, year, major);
    }

    public static Employee createTeacher(int id, String firstName, String lastName, String login, String password) {
        return new Teacher(id, firstName, lastName, login, password, Language.EN, 0, "");
    }

    public static Employee createManager(int id, String firstName, String lastName, String login, String password) {
        return new Manager(id, firstName, lastName, login, password, Language.EN, 0, "");
    }

    public static Employee createAdmin(int id, String firstName, String lastName, String login, String password) {
        return new Admin(id, firstName, lastName, login, password, Language.EN, 0, "");
    }
}
