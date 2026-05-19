package university.models.other;

import university.enums.RequestStatus;
import university.models.users.Employee;

import java.util.Objects;

public class TechRequest {
    private static int idCounter = 1;

    private final int requestId;
    private final String description;
    private RequestStatus status;
    private final Employee createdBy;

    public TechRequest(String description, Employee createdBy) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank");
        }
        this.requestId = idCounter++;
        this.description = description;
        this.createdBy = Objects.requireNonNull(createdBy, "Creator cannot be null");
        this.status = RequestStatus.NEW;
    }

    public void markViewed() {
        requireStatus(RequestStatus.NEW, "mark as viewed");
        status = RequestStatus.VIEWED;
        System.out.printf("[TechRequest #%d] Marked as VIEWED.%n", requestId);
    }

    public void accept() {
        requireStatus(RequestStatus.VIEWED, "accept");
        status = RequestStatus.ACCEPTED;
        System.out.printf("[TechRequest #%d] ACCEPTED.%n", requestId);
    }

    public void reject() {
        requireStatus(RequestStatus.VIEWED, "reject");
        status = RequestStatus.REJECTED;
        System.out.printf("[TechRequest #%d] REJECTED.%n", requestId);
    }

    public void markDone() {
        requireStatus(RequestStatus.ACCEPTED, "mark as done");
        status = RequestStatus.DONE;
        System.out.printf("[TechRequest #%d] Marked as DONE.%n", requestId);
    }

    // Returns true if this request is still open (not DONE or REJECTED)
    public boolean isOpen() {
        return status != RequestStatus.DONE && status != RequestStatus.REJECTED;
    }

    public void requireStatus(RequestStatus required, String action) {
        if(status != required) {
            throw new IllegalStateException(String.format(
                    "Cannot %s TechRequest #%d: expected status %s but was %s",
                    action, requestId, required, status
            ));
        }
    }

    @Override
    public String toString() {
        return String.format(
                "TechRequest{id=%d, status=%s, createdBy='%s %s', description='%s'}",
                requestId, status,
                createdBy.getFirstName(), createdBy.getLastName(),
                description
        );
    }

    public int getRequestId() { return requestId; }
    public String  getDescription() { return description; }
    public RequestStatus getStatus() { return status; }
    public Employee  getCreatedBy() { return createdBy; }
}
