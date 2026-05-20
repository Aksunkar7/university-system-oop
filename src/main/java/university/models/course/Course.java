package university.models.course;

import university.enums.CourseType;

public class Course {

    private String courseId;
    private String name;
    private int credits;
    private CourseType courseType;
    private int cntCourseLecture;
    private int cntCoursePractice;

    public Course(String courseId, String name, int credits, CourseType courseType,
                  int cntCourseLecture, int cntCoursePractice) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.courseType = courseType;
        this.cntCourseLecture = cntCourseLecture;
        this.cntCoursePractice = cntCoursePractice;
    }

    public String getCourseId() { return courseId; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public CourseType getCourseType() { return courseType; }
    public int getCntCourseLecture() { return cntCourseLecture; }
    public int getCntCoursePractice() { return cntCoursePractice; }

    public void setName(String name) { this.name = name; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setCntCourseLecture(int cntCourseLecture) { this.cntCourseLecture = cntCourseLecture; }
    public void setCntCoursePractice(int cntCoursePractice) { this.cntCoursePractice = cntCoursePractice; }

    public String getInfo() {
        return "Course{id='" + courseId + "', name='" + name +
                "', credits=" + credits + ", type=" + courseType + "}";
    }

    @Override
    public String toString() { return getInfo(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return this.courseId.equals(course.courseId);
    }

    @Override
    public int hashCode() { return courseId.hashCode(); }
}