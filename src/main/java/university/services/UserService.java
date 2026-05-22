package university.services;

import university.models.users.*;
import university.patterns.DataStorage;
import java.util.ArrayList;
import java.util.List;
import university.models.other.Message;
public class UserService {
    private DataStorage db = DataStorage.getInstance();

    public void addUser(User user) {
        db.getUsers().add(user);
        log("Added user: " + user.getFirstName());
    }

    public void removeUser(int id) {
        db.getUsers().removeIf(u -> u.getId() == id);
        log("Removed user with id: " + id);
    }

    public void updateUser(User user) {
        log("Updated user: " + user.getFirstName());
    }

    public User getUserById(int id) {
        for (User user : db.getUsers()) {
            if (user.getId() == id) return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return db.getUsers();
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        for (User user : db.getUsers()) {
            if (user instanceof Student) students.add((Student) user);
        }
        return students;
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        for (User user : db.getUsers()) {
            if (user instanceof Teacher) teachers.add((Teacher) user);
        }
        return teachers;
    }

    public void log(String action) {
        db.getLogs().add(action);
        System.out.println("LOG: " + action);
    }

    public List<String> viewLogs() {
        return db.getLogs();
    }

    public Message sendMessage(Employee from, Employee to, String content) {
        Message message = new Message(from, to, content);
        db.getMessages().add(message);
        db.log(from.getFirstName() + " sent message to " + to.getFirstName());
        System.out.println("Message sent!");
        return message;
    }

    public List<Message> getMessagesForEmployee(Employee employee) {
        List<Message> result = new ArrayList<>();
        for (Message m : db.getMessages()) {
            if (m.getTo().equals(employee)) {
                result.add(m);
            }
        }
        return result;
    }
}
