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
