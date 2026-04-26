package university.services;

import university.models.users.*;
import university.patterns.DataStorage;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private DataStorage db = DataStorage.getInstance();
    private List<String> logs = new ArrayList<>();

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
        logs.add(action);
        System.out.println("LOG: " + action);
    }

    public List<String> viewLogs() {
        return logs;
    }
}
