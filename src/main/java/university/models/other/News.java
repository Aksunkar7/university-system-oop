package university.models.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class News {
    private String title;
    private String content;
    private String topic;
    private final List<String> comments;
    private boolean isPinned;
    private final Date date;

    public News(String title, String content, String topic) {
        this.title = Objects.requireNonNull(title,   "Title cannot be null");
        this.content  = Objects.requireNonNull(content, "Content cannot be null");
        this.topic = Objects.requireNonNull(topic,   "Topic cannot be null");
        this.comments = new ArrayList<>();
        this.isPinned = false;
        this.date = new Date();
    }

        public void addComment(String comment) {
        if(comment == null || comment.isBlank()) {
            throw new IllegalArgumentException("Comment can't be blank");
        }
        comments.add(comment);
        }

        public void pin() {
            if(!isPinned) {
                isPinned = true;
                System.out.printf("[News] \"%s\" has been pinned.%n", title);
            }
        }

        public void unpin() {
            if(isPinned) {
                isPinned = false;
                System.out.printf("[News] \"%s\" has been unpinned.%n", title);
            }
        }

    @Override
    public String toString() {
        return String.format(
                "News{title='%s', topic='%s', pinned=%b, comments=%d, date=%s}",
                title, topic, isPinned, comments.size(), date
        );
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getTopic() { return topic; }
    public boolean isPinned() { return isPinned; }
    public Date getDate() { return new Date(date.getTime()); }
    public List<String> getComments() { return Collections.unmodifiableList(comments); }

    public void setTitle(String title) { this.title   = Objects.requireNonNull(title); }
    public void setContent(String content) { this.content = Objects.requireNonNull(content); }
    public void setTopic(String topic) { this.topic   = Objects.requireNonNull(topic); }
}
