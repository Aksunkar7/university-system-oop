package university.models.research;

import university.enums.CitationFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ResearchPaper implements Comparable<ResearchPaper>{
    private String title;
    private List<String> authors;
    private String journal;
    private Date date;
    private int citations;
    private String doi;
    private int pages;
    
    public ResearchPaper(String title) {
        this.title  = Objects.requireNonNull(title, "Title cannot be null");
        this.authors = new ArrayList<>();
        this.citations = 0;
        this.pages = 0;
        this.date = new Date();
    }

    public ResearchPaper(String title, List<String> authors, String journal, Date date, String doi, int pages) {
        this(title);
        this.authors = new ArrayList<>(authors);
        this.journal = journal;
        this.date = date;
        this.doi = doi;
        setPages(pages); // uses validated setter
    }

    public void addAuthor(String author) {
        if (author != null && !author.isBlank()) {
            authors.add(author);
        }
    }

    public String getCitation(CitationFormat format) {
        String authorStr = authors.isEmpty() ? "Unknown" : String.join(", ", authors);
        int  year = (date != null) ? (date.getYear() + 1900) : 0;
        String doiStr = (doi != null && !doi.isBlank()) ? doi : "N/A";
        String journalStr = (journal != null && !journal.isBlank()) ? journal : "Unknown Journal";

        return switch (format) {
            case PLAIN_TEXT -> String.format(
                    "%s. \"%s\". %s (%d). DOI: %s",
                    authorStr, title, journalStr, year, doiStr
            );
            case BIBTEX -> {
                String firstSurname = authors.isEmpty() ? "unknown" : authors.get(0).trim().split("\\s+")[0].toLowerCase();
                yield String.format(
                        "@article{%s%d,%n" +
                                "  author  = {%s},%n" +
                                "  title   = {%s},%n" +
                                "  journal = {%s},%n" +
                                "  year    = {%d},%n" +
                                "  doi     = {%s}%n" +
                                "}",
                        firstSurname, year,
                        authorStr, title, journalStr, year, doiStr
                );
            }
        };
    }

    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper other)) return false;
        if (doi != null && other.doi != null) return doi.equals(other.doi);
        return title.equalsIgnoreCase(other.title);
    }

    @Override
    public int hashCode() {
        return doi != null ? doi.hashCode() : title.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return String.format("ResearchPaper{title='%s', authors=%s, journal='%s', citations=%d}",
                title, authors, journal, citations);
    }

    public String getTitle() { return title; }
    public List<String> getAuthors() { return new ArrayList<>(authors); } // defensive copy
    public String getJournal() { return journal; }
    public Date getDate() { return new Date(date.getTime()); }  // defensive copy
    public int getCitations() { return citations; }
    public String getDoi() { return doi; }
    public int getPages() { return pages; }

    public void setJournal(String journal) { this.journal = journal; }
    public void setDate(Date date) { this.date = new Date(date.getTime()); }
    public void setDoi(String doi) { this.doi = doi; }

    public void setCitations(int citations) {
        if (citations < 0) throw new IllegalArgumentException("Citations cannot be negative");
        this.citations = citations;
    }

    public void setPages(int pages) {
        if (pages < 0) throw new IllegalArgumentException("Pages cannot be negative");
        this.pages = pages;
    }
}
