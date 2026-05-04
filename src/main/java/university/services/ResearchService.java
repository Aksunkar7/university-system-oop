package university.services;

import university.enums.CitationFormat;
import university.exceptions.NonResearcherException;
import university.interfaces.Researcher;
import university.models.research.ResearchPaper;
import university.models.research.ResearchProject;

import java.util.Comparator;


public class ResearchService {
    private Comparator<ResearchPaper> c;

//    public ResearchService(){}


    public void publishPaper(Researcher r, ResearchPaper paper) {
    }

    public int calculateHIndex(Researcher r) {
        return 0;
    }

    // Strategy pattern
    public void printPapers(Researcher r, Comparator<ResearchPaper> c) {
    }


    public void joinResearchProject(Researcher r, ResearchProject p)
            throws NonResearcherException {
    }

    public Researcher getTopCitedResearcher() {
        return null;
    }


    public Researcher getTopCitedByYear(int year) {
        return null;
    }


    public String getCitation(ResearchPaper paper, CitationFormat format) {
        return null;
    }
}