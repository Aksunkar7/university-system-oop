package university.services;

import university.enums.RequestStatus;
import university.models.other.TechRequest;
import university.models.users.Employee;
import university.patterns.DataStorage;

import java.util.List;

public class TechSupportService {
    private DataStorage db = DataStorage.getInstance();

    public TechRequest sendRequest(Employee e, String description) {
        TechRequest request = new TechRequest(description, e);
        db.getTechRequests().add(request);
        System.out.println("Request created: " + request);
        return request;
    }

    public List<TechRequest> viewRequests() {
        // статус NEW → VIEWED при просмотре
        for (TechRequest r : db.getTechRequests()) {
            if (r.getStatus() == RequestStatus.NEW) {
                r.setStatus(RequestStatus.VIEWED);
            }
        }
        return db.getTechRequests();
    }

    public void acceptRequest(TechRequest r) {
        r.setStatus(RequestStatus.ACCEPTED);
        System.out.println("Request accepted: " + r);
    }

    public void rejectRequest(TechRequest r) {
        r.setStatus(RequestStatus.REJECTED);
        System.out.println("Request rejected: " + r);
    }

    public void completeRequest(TechRequest r) {
        r.setStatus(RequestStatus.DONE);
        System.out.println("Request completed: " + r);
    }
}