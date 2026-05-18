package university.models.users;

import university.enums.Language;

public class Admin extends Employee {

    public Admin(int id, String firstName, String lastName, String login, String password, Language language, double salary, String department) {
        super(id, firstName, lastName, login, password, language, salary, department);
    }

    @Override
    public String getInfo() {
        return "Admin: " + getFirstName() + " " + getLastName();
    }

    @Override
    public String toString() {
        return getInfo();
    }
}