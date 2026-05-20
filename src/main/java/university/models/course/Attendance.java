package university.models.course;

import java.util.Date;

public class Attendance {

    private EnrollmentCourse enrollment;
    private Date date;
    private boolean isPresent;

    public Attendance(EnrollmentCourse enrollment, Date date, boolean isPresent) {
        this.enrollment = enrollment;
        this.date = date;
        this.isPresent = isPresent;
    }

    public EnrollmentCourse getEnrollment() { return enrollment; }
    public Date getDate() { return date; }
    public boolean isPresent() { return isPresent; }
    public void setPresent(boolean present) { isPresent = present; }

    @Override
    public String toString() {
        return "Attendance{course=" + enrollment.getCourse().getName() +
                ", date=" + date +
                ", present=" + isPresent + "}";
    }
}