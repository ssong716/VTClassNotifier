package cs3714.finalproject.vtclassnotifier;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AJ on 4/22/2016.
 */
public class CourseInfo implements Serializable{
    int crn, courseNumber, creditHours, openSeats, capacity;
    String department, title, instructor, days, times, location;
    ClassType classType;
    boolean hasAdditionalTime = false;
    String addDays = "", addTimes = "", addLocation = "";
    Campus campus;
    Term term;
    int year;


    private void writeObject(ObjectOutputStream oos)
            throws IOException {
        // default serialization
        oos.defaultWriteObject();
        // write the object
        List loc = toArrayList();
        oos.writeObject(loc);
    }

    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        // default deserialization
        ois.defaultReadObject();
        List loc = (List)ois.readObject(); // Replace with real deserialization
        readFromList(loc);
        // ... more code

    }

    public ArrayList<String> toArrayList()
    {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(String.valueOf(crn));//0
        retVal.add(String.valueOf(courseNumber));//1
        retVal.add(String.valueOf(creditHours));//2
        retVal.add(String.valueOf(openSeats));//3
        retVal.add(String.valueOf(capacity));//4
        retVal.add(department);//5
        retVal.add(title);//6
        retVal.add(instructor);//7
        retVal.add(days);//8
        retVal.add(times);//9
        retVal.add(location);//10
        retVal.add(classType.toString());//11
        retVal.add(String.valueOf(hasAdditionalTime));//12
        retVal.add(addDays);//13
        retVal.add(addTimes);//14
        retVal.add(addLocation);//15
        retVal.add(campus.toString());//16
        retVal.add(term.toString());//17
        retVal.add(String.valueOf(year));//18
        return retVal;
    }
    public CourseInfo(List<String> arrayList)
    {
        readFromList(arrayList);
    }

    public void readFromList(List<String> arrayList)
    {
        if(arrayList.size() == 19) {
            crn = Integer.parseInt(arrayList.get(0));
            courseNumber = Integer.parseInt(arrayList.get(1));
            creditHours = Integer.parseInt(arrayList.get(2));
            openSeats = Integer.parseInt(arrayList.get(3));
            capacity = Integer.parseInt(arrayList.get(4));
            department = arrayList.get(5);
            title = arrayList.get(6);
            instructor = arrayList.get(7);
            days = arrayList.get(8);
            times = arrayList.get(9);
            location = arrayList.get(10);
            classType = ClassType.toEnum(arrayList.get(11));
            hasAdditionalTime = Boolean.parseBoolean(arrayList.get(12));
            addDays = arrayList.get(13);
            addTimes = arrayList.get(14);
            addLocation = arrayList.get(15);
            campus = Campus.toEnum(arrayList.get(16));
            term = Term.toEnum(arrayList.get(17));
            year = Integer.parseInt(arrayList.get(18));
        }
    }

    public CourseInfo(ArrayList<String> arrayList, Term t, int y, Campus c)
    {
        if(arrayList.size() == 13)
        {
            //regular class
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
            term = t;
            year = y;
            campus = c;
        }
        else if(arrayList.size() == 12)
        {
            //online class
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
            days = "";
            times = "";
            location = "";
            term = t;
            year = y;
            campus = c;
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
        return  crn +"-(" +classType.toPrettyString() + ") "+ instructor+" "+  days + " " +times;
    }

    public String toDebugString()
    {
        if(classType != ClassType.ONLINE_COURSE) {
            String str = crn + " | " + department + courseNumber + " | " + title + " | " + classType.toString() + " | " + creditHours + " | " + openSeats + " | " + capacity + " | " + instructor + " | " + days + " | " + times + " | " + location;
            str += "\n" + term.name() + year + " | " + campus.name();
            if (hasAdditionalTime) {
                str += "\nAdditional Times: " + addDays + " | " + addTimes + " | " + addLocation;
            }
            return str;
        }
        else
        {
            String str = crn + " | " + department + courseNumber + " | " + title + " | " + classType.toString() + " | " + creditHours + " | " + openSeats + " | " + capacity + " | " + instructor;
            str += "\n" + term.name() + year + " | " + campus.name();
            if (hasAdditionalTime) {
                str += "\nAdditional Times: " + addDays + " | " + addTimes + " | " + addLocation;
            }
            return str;
        }
    }

    public Query toQuery()
    {
        Query retVal = new Query();
        retVal.setCampus(campus.toInt());
        retVal.setTermyear(term.toInt(year));
        retVal.setCrn(crn);
        return retVal;
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
