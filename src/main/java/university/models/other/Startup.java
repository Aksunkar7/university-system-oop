package university.models.other;

import university.models.users.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Startup {
    private String name;
    private final Student founder;      // immutable: founders don't change
    private String description;
    private final List<Student> members;

    public Startup(String name, Student founder, String description) {
        this.name = Objects.requireNonNull(name,        "Name cannot be null");
        this.founder = Objects.requireNonNull(founder,     "Founder cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.members = new ArrayList<>();
        members.add(founder); // founder is always a member
    }

    public void addMember(Student student) {
        Objects.requireNonNull(student, "Student cannot be null");
        if (!members.contains(student)) {
            members.add(student);
            System.out.printf("[Startup: %s] %s %s joined the team.%n",
                    name, student.getFirstName(), student.getLastName());
        }
    }

    public void removeMember(Student student) {
        Objects.requireNonNull(student, "Student cannot be null");
        if (student.equals(founder)) {
            throw new IllegalStateException("The founder cannot be removed from the startup.");
        }
        if (members.remove(student)) {
            System.out.printf("[Startup: %s] %s %s left the team.%n",
                    name, student.getFirstName(), student.getLastName());
        }
    }

    public boolean hasMember(Student student) {
        return members.contains(student);
    }


    @Override
    public String toString() {
        return String.format(
                "Startup{name='%s', founder='%s %s', members=%d, description='%s'}",
                name,
                founder.getFirstName(), founder.getLastName(),
                members.size(),
                description
        );
    }

    public String getName() { return name; }
    public Student getFounder() { return founder; }           // immutable, no setter
    public String getDescription() { return description; }
    public List<Student> getMembers() { return Collections.unmodifiableList(members); }

    public void setName(String name) { this.name        = Objects.requireNonNull(name); }
    public void setDescription(String desc) { this.description = Objects.requireNonNull(desc); }

}
