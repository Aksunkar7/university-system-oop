package university.models.research;

public class ResearchPaper {
    private String title;
    private String journal;
    private int citations;
    private int pages;
    
    public ResearchPaper(String title) {
        this.title = title;
        this.citations = 0;
        this.pages = 0;
    }
    
    public String getTitle() { return title; }
    public String getJournal() { return journal; }
    public int getCitations() { return citations; }
    public int getPages() { return pages; }
    public void setCitations(int citations) { this.citations = citations; }
}
