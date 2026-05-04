package university.models.research;

import university.models.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Journal {
    private final String name;
    private final List<ResearchPaper> papers;
    private final List<User> subscribers;

    public Journal(String name) {
        this.name  = Objects.requireNonNull(name, "Journal name cannot be null");
        this.papers = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("Journal{name='%s', papers=%d, subscribers=%d}",
                name, papers.size(), subscribers.size());
    }

    //no setters cause they will be implemenented via other methods like publishPaper

    public String getName() { return name; }
    public List<ResearchPaper> getPapers() { return Collections.unmodifiableList(papers); }
    public List<User> getSubscribers() { return Collections.unmodifiableList(subscribers); }
    public int getSubscriberCount() { return subscribers.size(); }
}
