package university.models.research;

import university.interfaces.Researcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ResearchProject {
    private String topic;
    private List<Researcher> participants;
    private List<ResearchPaper> papers;

    public ResearchProject(String topic) {
        this.topic = topic;
        this.participants = new ArrayList<>();
        this.papers = new ArrayList<>();
    }

    public String getTopic() { return topic; }
    public List<Researcher> getParticipants() { return participants; }
    public List<ResearchPaper> getPapers() { return papers; }

    public List<ResearchPaper> getPapersSortedByCitations() {
        List<ResearchPaper> sorted = new ArrayList<>(papers);
        Collections.sort(sorted); // uses ResearchPaper.compareTo()
        return Collections.unmodifiableList(sorted);
    }

    public int getTotalCitations() {
        return papers.stream().mapToInt(ResearchPaper::getCitations).sum();
    }

    public boolean hasParticipant(Researcher researcher) {
        return participants.contains(researcher);
    }

    @Override
    public String toString() {
        return String.format(
                "ResearchProject{topic='%s', participants=%d, papers=%d, totalCitations=%d}",
                topic, participants.size(), papers.size(), getTotalCitations()
        );
    }
}
