package university.models.course;

public class Room {

    private String roomNumber;
    private int capacity;
    private boolean hasProjector;

    public Room(String roomNumber, int capacity, boolean hasProjector) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
    }

    public String getRoomNumber() { return roomNumber; }
    public int getCapacity() { return capacity; }
    public boolean isHasProjector() { return hasProjector; }

    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setHasProjector(boolean hasProjector) { this.hasProjector = hasProjector; }

    @Override
    public String toString() {
        return "Room{number='" + roomNumber + "', capacity=" + capacity +
                ", projector=" + hasProjector + "}";
    }
}