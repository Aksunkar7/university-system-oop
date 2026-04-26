package university.models.users;

public abstract class Employee extends User {
    private double salary;
    private String department;

    public Employee(int id, String firstName, String lastName, String login, String password, university.enums.Language language, double salary, String department) {
        super(id, firstName, lastName, login, password, language);
        this.salary = salary;
        this.department = department;
    }

    public void sendMessage(Employee to, String text) {
        System.out.println("Message from " + getFirstName() + " to " + to.getFirstName() + ": " + text);
    }

    @Override
    public String getInfo() {
        return "Employee: " + getFirstName() + " " + getLastName() + ", department: " + department;
    }

    public double getSalary() { return salary; }
    public String getDepartment() { return department; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setDepartment(String department) { this.department = department; }
}
