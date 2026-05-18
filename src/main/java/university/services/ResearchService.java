package university.services;

import university.enums.CitationFormat;
import university.exceptions.NonResearcherException;
import university.interfaces.Researcher;
import university.models.other.News;
import university.models.research.ResearchPaper;
import university.models.research.ResearchProject;
import university.patterns.DataStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.Comparator;


public class ResearchService {

    private final DataStorage dataStorage;
    private final NewsService newsService;

    public ResearchService(DataStorage dataStorage, NewsService newsService) {
        this.dataStorage = dataStorage;
        this.newsService = newsService;
    }

    public void publishPaper(Researcher r, ResearchPaper paper) {
        if (r == null)     throw new IllegalArgumentException("Researcher cannot be null");
        if (paper == null) throw new IllegalArgumentException("Paper cannot be null");

        // add to researcher's own list
        r.addPaper(paper);

        // auto create a pinned Research news post
        String newsTitle   = "New Research Paper Published: \"" + paper.getTitle() + "\"";
        String newsContent = "A new paper has been published. " + paper.toString();
        newsService.createNews(newsTitle, newsContent, "Research");

        System.out.printf("[ResearchService] Paper \"%s\" published by researcher.%n",
                paper.getTitle());
    }

    public int calculateHIndex(Researcher r) {
        if (r == null) throw new IllegalArgumentException("Researcher cannot be null");

        List<ResearchPaper> sorted = new ArrayList<>(r.getPapers());
        sorted.sort(Comparator.comparingInt(ResearchPaper::getCitations).reversed());

        int h = 0;
        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getCitations() >= i + 1) {
                h = i + 1;
            } else {
                break;
            }
        }
        return h;
    }

    public void printPapers(Researcher r, Comparator<ResearchPaper> c) {
        if (r == null) throw new IllegalArgumentException("Researcher cannot be null");
        if (c == null) throw new IllegalArgumentException("Comparator cannot be null");
        r.printPapers(c);
    }


    public void joinResearchProject(Researcher r, ResearchProject p)
            throws NonResearcherException {
        if (r == null) {
            throw new NonResearcherException(
                    "Cannot join project: provided object is not a valid Researcher.");
        }
        if (p == null) throw new IllegalArgumentException("ResearchProject cannot be null");

        p.addParticipant(r);
        System.out.printf("[ResearchService] Researcher joined project \"%s\"%n", p.getTopic());
    }

    public Researcher getTopCitedResearcher() {
        List<Researcher> researchers = dataStorage.getResearchers();
        if (researchers.isEmpty()) {
            System.out.println("[ResearchService] No researchers found.");
            return null;
        }

        Researcher top = null;
        int max = -1;

        for (Researcher r : researchers) {
            int total = r.getPapers().stream().mapToInt(ResearchPaper::getCitations).sum();
            if (total > max) {
                max = total;
                top = r;
            }
        }
        return top;
    }


    public Researcher getTopCitedByYear(int year) {
        List<Researcher> researchers = dataStorage.getResearchers();
        if (researchers.isEmpty()) {
            System.out.println("[ResearchService] No researchers found.");
            return null;
        }

        Researcher top = null;
        int maxSum = -1;

        for (Researcher r : researchers) {
            // Sum only papers whose publication year matches
            int yearSum = r.getPapers().stream()
                    .filter(p -> p.getDate() != null && (p.getDate().getYear() + 1900) == year)
                    .mapToInt(ResearchPaper::getCitations).sum();

            if (yearSum > maxSum) {
                maxSum = yearSum;
                top = r;
            }
        }
        return top;
    }


    public String getCitation(ResearchPaper paper, CitationFormat format) {
        if (paper  == null) throw new IllegalArgumentException("Paper cannot be null");
        if (format == null) throw new IllegalArgumentException("Format cannot be null");
        return paper.getCitation(format);
    }
}