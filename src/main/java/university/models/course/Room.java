package university.models.course;

import university.enums.RoomType;

public class Room {

    private String roomNumber;
    private int capacity;
    private RoomType roomType;

    public Room(String roomNumber, int capacity, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.roomType = roomType;
    }

    public String getRoomNumber() { return roomNumber; }
    public int getCapacity() { return capacity; }
    public RoomType getRoomType() { return roomType; }

    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }

    @Override
    public String toString() {
        return "Room{number='" + roomNumber + "', capacity=" + capacity +
                ", type=" + roomType + "}";
    }
}