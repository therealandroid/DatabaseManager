package br.com.diogojayme.databasemanager.table;

import diogo.tablegenerator.annotations.Column;
import diogo.tablegenerator.annotations.ForeignKey;
import diogo.tablegenerator.annotations.PrimaryKey;
import diogo.tablegenerator.annotations.Table;

/**
 * Created by diogojayme on 9/13/16.
 */
@Table(name = "user")
public class User {

    @PrimaryKey
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ForeignKey(name = "university_id", references = University.class)
    private Long universityId;

    @ForeignKey(name = "course_id", references = Course.class)
    private Long courseId;

    private Course course;

    private University university;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(long universityId) {
        this.universityId = universityId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
}
