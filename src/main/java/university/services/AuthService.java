package university.services;

import university.models.users.User;
import university.patterns.DataStorage;
import java.util.List;

public class AuthService {
    private DataStorage db = DataStorage.getInstance();

    public User login(String login, String password) {
        List<User> users = db.getUsers();
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                System.out.println("Welcome, " + user.getFirstName() + "!");
                return user;
            }
        }
        System.out.println("Invalid login or password");
        return null;
    }

    public void logout(User user) {
        System.out.println(user.getFirstName() + " logged out");
    }
}
