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
        this.name        = Objects.requireNonNull(name, "Journal name cannot be null");
        this.papers      = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("Journal{name='%s', papers=%d, subscribers=%d}",
                name, papers.size(), subscribers.size());
    }
}
