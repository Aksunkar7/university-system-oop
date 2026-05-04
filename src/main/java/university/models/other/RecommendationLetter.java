package university.models.other;

import university.models.users.Student;
import university.models.users.Teacher;

import java.util.Date;
import java.util.Objects;

public class RecommendationLetter {
    private final Teacher author;
    private final Student recipient;
    private String content;   // mutable to support draft editing
    private final Date date;

    public RecommendationLetter(Teacher author, Student recipient, String content) {
        this.author = Objects.requireNonNull(author,    "Author cannot be null");
        this.recipient = Objects.requireNonNull(recipient, "Recipient cannot be null");

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Letter content cannot be blank");
        }
        this.content = content;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return String.format(
                "=== Recommendation Letter ===%n" +
                        "Date:      %s%n" +
                        "Author:    %s %s (%s)%n" +
                        "Recipient: %s %s%n" +
                        "---%n" +
                        "%s%n" +
                        "============================",
                date,
                author.getFirstName(), author.getLastName(), author.getPosition(),
                recipient.getFirstName(), recipient.getLastName(),
                content
        );
    }

    public Teacher getAuthor() { return author; }
    public Student getRecipient() { return recipient; }
    public String  getContent() { return content; }
    public Date  getDate() { return new Date(date.getTime()); }

}
