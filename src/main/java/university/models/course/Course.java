package university.models.course;

import university.enums.CourseType;

public class Course {

    private int id;
    private String name;
    private int credits;
    private CourseType courseType;
    private String description;

    public Course(int id, String name, int credits, CourseType courseType, String description) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.courseType = courseType;
        this.description = description;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public CourseType getCourseType() { return courseType; }
    public String getDescription() { return description; }

    public void setName(String name) { this.name = name; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Course{id=" + id + ", name='" + name + "', credits=" + credits +
                ", type=" + courseType + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return this.id == course.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}