package university.models.users;

import university.enums.Language;

public class TechSupportSpecialist extends Employee {

    public TechSupportSpecialist(int id, String firstName, String lastName, String login, String password, Language language, double salary, String department) {
        super(id, firstName, lastName, login, password, language, salary, department);
    }

    @Override
    public String getInfo() {
        return "TechSupportSpecialist: " + getFirstName() + " " + getLastName();
    }

    @Override
    public String toString() {
        return getInfo();
    }
}