package cs3714.finalproject.vtclassnotifier;

/**
 * Created by AJ on 4/24/2016.
 */
public enum Campus {
    BLACKSBURG,
    VIRTUAL,
    WESTERN,
    VALLEY,
    NATIONAL_CAPITAL_REGION,
    CENTRAL,
    HAMPTON_ROADS_CENTER,
    CAPITAL,
    OTHER;

    public int toInt()
    {
        switch(this)
        {
            case BLACKSBURG:
                return 0;
            case VIRTUAL:
                return 10;
            case WESTERN:
                return 2;
            case VALLEY:
                return 3;
            case NATIONAL_CAPITAL_REGION:
                return 4;
            case CENTRAL:
                return 6;
            case HAMPTON_ROADS_CENTER:
                return 7;
            case CAPITAL:
                return 8;
            case OTHER:
                return 9;
        }
        return -1;
    };

    public static Campus toEnum(String s)
    {
      if(s.contains("Blacksburg"))
      {
          return BLACKSBURG;
      }
        else if(s.contains("Virtual"))
      {
          return VIRTUAL;
      }
        else if (s.contains("Western"))
      {
          return WESTERN;
      }
        else if(s.contains("Valley"))
      {
          return VALLEY;
      }
        else if(s.contains("Central"))
      {
          return CENTRAL;
      }
        else if(s.contains("Capital"))
      {
          return CAPITAL;
      }
        else if(s.contains("Other"))
      {
          return OTHER;
      }
        else if(s.contains("National"))
      {
          return NATIONAL_CAPITAL_REGION;
      }
        else
      {
          return HAMPTON_ROADS_CENTER;
      }

    };

}
