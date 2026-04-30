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
}
