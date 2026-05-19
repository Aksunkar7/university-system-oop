package university.models.course;

import university.models.users.Teacher;

import java.util.Date;

public class Schedule {

    private Course course;
    private Teacher teacher;
    private Room room;
    private Date dateTime;

    public Schedule(Course course, Teacher teacher, Room room, Date dateTime) {
        this.course = course;
        this.teacher = teacher;
        this.room = room;
        this.dateTime = dateTime;
    }

    public Course getCourse() { return course; }
    public Teacher getTeacher() { return teacher; }
    public Room getRoom() { return room; }
    public Date getDateTime() { return dateTime; }

    public void setCourse(Course course) { this.course = course; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    public void setRoom(Room room) { this.room = room; }
    public void setDateTime(Date dateTime) { this.dateTime = dateTime; }

    @Override
    public String toString() {
        return "Schedule{course=" + course.getName() +
                ", room=" + room.getRoomNumber() +
                ", dateTime=" + dateTime + "}";
    }
}