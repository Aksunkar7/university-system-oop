package university.patterns;

import university.models.users.User;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static DataStorage instance;
    // Пока что обжект қолдана тұрамыз, ол модельдер жоқ кезде
    private List<User> users;
    private List<Object> courses;
    private List<Object> enrollments;
    private List<Object> news;
    private List<Object> journals;
    private List<Object> techRequests;
    private List<Object> researchPapers;
    private List<Object> researchProjects;
    private List<String> logs;

    private DataStorage() {
        users = new ArrayList<>();
        courses = new ArrayList<>();
        enrollments = new ArrayList<>();
        news = new ArrayList<>();
        journals = new ArrayList<>();
        techRequests = new ArrayList<>();
        researchPapers = new ArrayList<>();
        researchProjects = new ArrayList<>();
        logs = new ArrayList<>();
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public List<User> getUsers() { return users; }
    public List<Object> getCourses() { return courses; }
    public List<Object> getEnrollments() { return enrollments; }
    public List<Object> getNews() { return news; }
    public List<Object> getJournals() { return journals; }
    public List<Object> getTechRequests() { return techRequests; }
    public List<Object> getResearchPapers() { return researchPapers; }
    public List<Object> getResearchProjects() { return researchProjects; }
    public List<String> getLogs() { return logs; }
}
