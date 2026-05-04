package university.models.other;

import university.models.users.Employee;

import java.util.Date;
import java.util.Objects;

public class Message {
    private final Employee from;
    private final Employee to;
    private final String content;
    private final Date date;


    public Message(Employee from, Employee to, String content) {
        this.from = Objects.requireNonNull(from,    "Sender cannot be null");
        this.to = Objects.requireNonNull(to,      "Recipient cannot be null");

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Message content cannot be blank");
        }
        this.content = content;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s → %s %s: %s",
                date,
                from.getFirstName(), from.getLastName(),
                to.getFirstName(),   to.getLastName(),
                content
        );
    }

    public Employee getFrom() { return from; }
    public Employee getTo() { return to; }
    public String getContent() { return content; }
    public Date getDate() { return new Date(date.getTime()); } // defensive copy
}
