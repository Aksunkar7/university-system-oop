package university.patterns;

import university.models.course.Course;
import university.models.course.EnrollmentCourse;
import university.models.course.Schedule;
import university.models.course.Attendance;
import university.models.other.*;
import university.models.research.Journal;
import university.interfaces.Researcher;
import university.models.research.ResearchPaper;
import university.models.research.ResearchProject;
import university.models.users.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataStorage {
    private static DataStorage instance;

    private List<User> users;
    private List<Course> courses;
    private List<EnrollmentCourse> enrollments;
    private List<News> news;
    private List<Journal> journals;
    private List<TechRequest> techRequests;
    private List<ResearchPaper> researchPapers;
    private List<ResearchProject> researchProjects;
    private List<Schedule> schedules;
    private List<Attendance> attendances;
    private List<Message> messages;
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
        schedules = new ArrayList<>();
        attendances = new ArrayList<>();
        messages = new ArrayList<>();
        logs = new ArrayList<>();
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public List<Researcher> getResearchers() {
        List<Researcher> researchers = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Researcher r) {   // pattern matching (Java 16+)
                researchers.add(r);
            }
        }
        return Collections.unmodifiableList(researchers);
    }

    public List<User> getUsers() { return users; }
    public List<Course> getCourses() { return courses; }
    public List<EnrollmentCourse> getEnrollments() { return enrollments; }
    public List<News> getNews() { return news; }
    public List<Journal> getJournals() { return journals; }
    public List<TechRequest> getTechRequests() { return techRequests; }
    public List<ResearchPaper> getResearchPapers() { return researchPapers; }
    public List<ResearchProject> getResearchProjects() { return researchProjects; }
    public List<Schedule> getSchedules() { return schedules; }
    public List<Attendance> getAttendances() { return attendances; }
    public List<Message> getMessages() { return messages; }
    public List<String> getLogs() { return logs; }

    public void log(String action) {
        logs.add(action);
    }
}
