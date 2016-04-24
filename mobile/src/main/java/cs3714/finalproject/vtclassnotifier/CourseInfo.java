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
    boolean hasAdditionalTime = false;
    String addDays, addTimes, addLocation;

    public CourseInfo(ArrayList<String> arrayList)
    {
        if(arrayList.size() == 13)
        {
            crn = Integer.parseInt(arrayList.get(0).substring(0, arrayList.get(0).length() - 1));
            String [] temp = arrayList.get(1).trim().split("-");
            if(temp.length == 2)
            {
                department = temp[0];
                courseNumber = Integer.parseInt(temp[1]);
            }
            title = arrayList.get(2);
            classType = ClassType.toEnum(arrayList.get(3).trim());
            creditHours = Integer.parseInt(arrayList.get(4).trim());
            if(arrayList.get(5).contains("Full"))
            {
                openSeats = Integer.parseInt(arrayList.get(5).trim().replace("Full ", ""));
            }
            else
            {
                openSeats = Integer.parseInt(arrayList.get(5).trim());
            }
            capacity = Integer.parseInt(arrayList.get(6).trim());
            instructor = arrayList.get(7).trim();
            days = arrayList.get(8).trim();
            times = arrayList.get(9).trim() + " - " + arrayList.get(10).trim();
            location = arrayList.get(11).trim();
        }
    }
    public void addTimes(String aDays, String bTime, String eTime, String aLocation)
    {
        hasAdditionalTime = true;
        addDays = aDays;
        addTimes = bTime + " - " + eTime;
        addLocation = aLocation;

    }
    public String toString()
    {
        String str = crn + " | " + department + courseNumber + " | " + title + " | " + classType.toString() + " | " + creditHours+ " | " + openSeats+ " | " + capacity+ " | " + instructor+ " | " +days + " | " + times+ " | " +location;
        if(hasAdditionalTime)
        {
            str += "\nAdditional Times: " + addDays + " | " + addTimes + " | " + addLocation;
        }
        return str;
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
