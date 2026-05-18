package university.models.users;

import university.enums.Language;
import java.io.Serializable;

public abstract class User implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private Language language;

    public User(int id, String firstName, String lastName, String login, String password, Language language) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.language = language;
    }

    public abstract String getInfo();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name=" + firstName + " " + lastName + "}";
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public Language getLanguage() { return language; }
    public void setLanguage(Language language) { this.language = language; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPassword(String password) { this.password = password; }
}