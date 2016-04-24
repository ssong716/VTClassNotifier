package cs3714.finalproject.vtclassnotifier;

/**
 * Created by AJ on 4/22/2016.
 */
public enum ClassType {
    LECTURE,
    LAB,
    INDEPENDENT_STUDY,
    RECITATION,
    RESEARCH,
    ONLINE_COURSE;
    public String toString()
    {
        switch(this)
        {
            case LECTURE:
                return "Lecture";
            case INDEPENDENT_STUDY:
                return "Independent Study";
            case RECITATION:
                return "Recitation";
            case RESEARCH:
                return "Research";
            case ONLINE_COURSE:
                return "Online Course";
            default:
                return "";
        }
    };

    public static ClassType toEnum(String s)
    {
        if(s.equalsIgnoreCase("L"))
        {
            return LECTURE;
        }
        else if(s.equalsIgnoreCase("B"))
        {
            return LAB;
        }
        else if(s.equalsIgnoreCase("I"))
        {
            return INDEPENDENT_STUDY;
        }
        else if(s.equalsIgnoreCase("C"))
        {
            return RECITATION;
        }
        else if(s.equalsIgnoreCase("R"))
        {
            return RESEARCH;
        }
        else
        {
            return ONLINE_COURSE;
        }
    };
};


