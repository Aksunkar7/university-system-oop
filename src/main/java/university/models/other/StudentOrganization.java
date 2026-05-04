package university.models.other;

import university.models.users.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StudentOrganization {
    private String name;
    private Student head;
    private final List<Student> members;

    public StudentOrganization(String name, Student head) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.members = new ArrayList<>();
        setHead(head);
    }

    public void addMember(Student student) {
        Objects.requireNonNull(student, "Student cannot be null");
        if (!members.contains(student)) {
            members.add(student);
            System.out.printf("[%s] %s %s joined.%n",
                    name, student.getFirstName(), student.getLastName());
        }
    }

    @Override
    public String toString() {
        return String.format(
                "StudentOrganization{name='%s', head='%s %s', members=%d}",
                name, head.getFirstName(), head.getLastName(), members.size()
        );
    }


    public String getName() { return name; }
    public Student getHead() { return head; }
    public List<Student> getMembers() { return Collections.unmodifiableList(members); }

    public void setName(String name) { this.name = Objects.requireNonNull(name); }


    public void setHead(Student newHead) {
        Objects.requireNonNull(newHead, "Head cannot be null");
        this.head = newHead;
        addMember(newHead);
    }
}
