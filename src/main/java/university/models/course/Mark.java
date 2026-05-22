package university.models.course;

public class Mark {
    public enum MarkStatus {
        PASS, FAIL, FX, RETAKE, NOT_ADMITTED
    }

    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    private EnrollmentCourse enrollment;
    private MarkStatus status;
    public Mark(EnrollmentCourse enrollment, double att1,
                double att2, double finalExam) {
        this.enrollment = enrollment;
        this.firstAttestation = att1;
        this.secondAttestation = att2;
        this.finalExam = finalExam;
        this.status = calculateStatus();
    }

    private MarkStatus calculateStatus() {
        // проверка допуска к финалу
        if (firstAttestation + secondAttestation <= 29.5) {
            return MarkStatus.NOT_ADMITTED;
        }
        // проверка финала
        if (finalExam < 9.5) {
            return MarkStatus.RETAKE;
        }
        if (finalExam < 19.5) {
            return MarkStatus.FX;
        }
        // итоговая проверка
        double total = getTotal();
        if (total < 50) return MarkStatus.FAIL;
        return MarkStatus.PASS;
    }

    public boolean isPassed() {
        return status == MarkStatus.PASS;
    }

    public MarkStatus getStatus() { return status; }

    public double getTotal() {
        return firstAttestation + secondAttestation + finalExam;
    }

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
                ", status=" + status + "}";
    }
}