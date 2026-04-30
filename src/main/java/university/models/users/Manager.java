package university.models.users;

import university.enums.Language;
import university.enums.ManagerType;

import java.util.List;

public class Manager extends Employee {

    private ManagerType managerType;

    public Manager(int id, String firstName, String lastName, String login,
                   String password, Language language,
                   double salary, String department,
                   ManagerType managerType) {
        super(id, firstName, lastName, login, password, language, salary, department);
        this.managerType = managerType;
    }

    public ManagerType getManagerType() { return managerType; }
    public void setManagerType(ManagerType managerType) { this.managerType = managerType; }

    public void approveStudentRequest(Student student) {
        System.out.println("Request approved for: " + student.getFirstName() + " " + student.getLastName());
    }

    public List<Student> viewAllStudents() {
        // логика в сервисе
        return null;
    }

    @Override
    public String getInfo() {
        return "Manager: " + getFirstName() + " " + getLastName() +
                ", type: " + managerType +
                ", department: " + getDepartment();
    }

    @Override
    public String toString() {
        return getInfo();
    }
}