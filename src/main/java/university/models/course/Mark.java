package university.models.course;

public class Mark {

    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    private EnrollmentCourse enrollment;

    public Mark(EnrollmentCourse enrollment, double firstAttestation, double secondAttestation, double finalExam) {
        this.enrollment = enrollment;
        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
    }

    public double getTotal() {
        return firstAttestation * 0.3 + secondAttestation * 0.3 + finalExam * 0.4;
    }

    public boolean isPassed() { return getTotal() >= 50.0; }

    public EnrollmentCourse getEnrollment() { return enrollment; }
    public double getFirstAttestation() { return firstAttestation; }
    public double getSecondAttestation() { return secondAttestation; }
    public double getFinalExam() { return finalExam; }

    public void setFirstAttestation(double firstAttestation) { this.firstAttestation = firstAttestation; }
    public void setSecondAttestation(double secondAttestation) { this.secondAttestation = secondAttestation; }
    public void setFinalExam(double finalExam) { this.finalExam = finalExam; }

    @Override
    public String toString() {
        return "Mark{course=" + enrollment.getCourse().getName() +
                ", att1=" + firstAttestation +
                ", att2=" + secondAttestation +
                ", final=" + finalExam +
                ", total=" + String.format("%.2f", getTotal()) +
                ", passed=" + isPassed() + "}";
    }
}