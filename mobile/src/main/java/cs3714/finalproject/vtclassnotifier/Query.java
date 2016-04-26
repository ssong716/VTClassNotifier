package cs3714.finalproject.vtclassnotifier;

/**
 * Created by AJ on 4/24/2016.
 */
public class Query {
    int campus = 0; //default is blacksburg
    int termyear = 201609; //default is fall 2016
    String subject = "%"; //default is cs
    String courseNum = "";
    String crn = "";
    //Complete the request paremeters from the fields above

    public String queryString()
    {
        return "CAMPUS=" + campus + "&TERMYEAR=" + termyear + "&CORE_CODE=AR%25&subj_code="
                + subject + "&SCHDTYPE=%25&CRSE_NUMBER=" + courseNum + "&crn=" + crn + "&open_only=&disp_comments_in=Y&BTN_PRESSED=FIND+class+sections&inst_name=";
    }

    public int getCampus() {
        return campus;
    }

    public void setCampus(int campus) {
        this.campus = campus;
    }

    public int getTermyear() {
        return termyear;
    }

    public void setTermyear(int termyear) {
        this.termyear = termyear;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCrn() {
        return Integer.parseInt(this.crn);
    }

    public void setCrn(int crn) {
        this.crn = String.valueOf(crn);
    }

    public int getCourseNum() {
        return Integer.parseInt(courseNum);
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = String.valueOf(courseNum);
    }


}
