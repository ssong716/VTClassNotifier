package cs3714.finalproject.vtclassnotifier;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by AJ on 4/22/2016.
 */
public class CourseInfo {
    int crn, courseNumber, creditHours, openSeats, capacity;
    String department, title, instructor, days, times, location;
    ClassType classType;
    String addDays, addTimes, addLocation;

    public CourseInfo(ArrayList<String> arrayList)
    {
        if(arrayList.size() == 13)
        {

        }
    }

    public int getCrn() {
        return crn;
    }

    public void setCrn(int crn) {
        this.crn = crn;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public int getOpenSeats() {
        return openSeats;
    }

    public void setOpenSeats(int openSeats) {
        this.openSeats = openSeats;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public String getAddDays() {
        return addDays;
    }

    public void setAddDays(String addDays) {
        this.addDays = addDays;
    }

    public String getAddTimes() {
        return addTimes;
    }

    public void setAddTimes(String addTimes) {
        this.addTimes = addTimes;
    }

    public String getAddLocation() {
        return addLocation;
    }

    public void setAddLocation(String addLocation) {
        this.addLocation = addLocation;
    }
}
