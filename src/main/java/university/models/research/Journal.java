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

    public void subscribe(User user) {
        Objects.requireNonNull(user, "User cannot be null");
        if (!subscribers.contains(user)) {
            subscribers.add(user);
            System.out.printf("[Journal: %s] %s %s subscribed.%n",
                    name, user.getFirstName(), user.getLastName());
        } else {
            System.out.printf("[Journal: %s] %s %s is already subscribed.%n",
                    name, user.getFirstName(), user.getLastName());
        }
    }

    public void unsubscribe(User user) {
        if (subscribers.remove(user)) {
            System.out.printf("[Journal: %s] %s %s unsubscribed.%n",
                    name, user.getFirstName(), user.getLastName());
        }
    }

    public void publishPaper(ResearchPaper paper) {
        Objects.requireNonNull(paper, "Paper can't be null");

        if (papers.contains(paper)) {
            System.out.printf("[Journal: %s] Paper \"%s\" already published here.%n",
                    name, paper.getTitle());
            return;
        }

        paper.setJournal(this.name);
        papers.add(paper);

        System.out.printf("[Journal: %s] Published: \"%s\"%n", name, paper.getTitle());

        // Observer push - notify all subscribers automatically
        notifySubscribers(paper);
    }

    public void notifySubscribers(ResearchPaper paper) {
        if (subscribers.isEmpty()) {
            System.out.printf("[Journal: %s] No subscribers to notify.%n", name);
            return;
        }
        System.out.printf("[Journal: %s] Notifying %d subscriber(s):%n",
                name, subscribers.size());
        for (User subscriber : subscribers) {
            System.out.printf("  → %s %s: New paper \"%s\" in \"%s\"%n",
                    subscriber.getFirstName(), subscriber.getLastName(),
                    paper.getTitle(), name);
        }
    }

    //general notification without a specific paper context
    public void notifySubscribers() {
        if (subscribers.isEmpty()) return;
        System.out.printf("[Journal: %s] Announcement to %d subscriber(s).%n",
                name, subscribers.size());
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
