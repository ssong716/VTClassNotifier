package cs3714.finalproject.vtclassnotifier;

/**
 * Created by AJ on 4/25/2016.
 */
public enum Term {
    SPRING ,
    SUMMER_I ,
    SUMMER_II,
    FALL;
    public int toInt(int year)
    {
        int retVal = year * 100;
        switch (this)
        {
            case SPRING:
                return retVal +1;
            case SUMMER_I:
                return retVal + 6;
            case SUMMER_II:
                return retVal + 7;
            case FALL:
                return retVal + 9;
        }
        return 0;
    }

    public String toString()
    {
        switch (this)
        {
            case SPRING:
                return "Spring";
            case SUMMER_I:
                return "Summer I";
            case SUMMER_II:
                return "Summer II";
            case FALL:
                return "Fall";
        }
        return "";
    }

    public static Term toEnum(String s)
    {
        if(s.contains("Fall"))
        {
            return Term.FALL;
        }
        else if(s.contains("Spring"))
        {
            return Term.SPRING;
        }
        else if(s.contains("Summer II"))
        {
            return Term.SUMMER_II;
        }
        else
        {
            return Term.SUMMER_I;
        }
    }
}
