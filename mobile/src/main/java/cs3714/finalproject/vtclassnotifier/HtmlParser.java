package cs3714.finalproject.vtclassnotifier;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AJ on 4/22/2016.
 */
public class HtmlParser {
    public HashMap<Integer, CourseInfo> parseTable(String html) throws Exception
    {
        Document doc = Jsoup.parse(html);
        ArrayList<String> temp = new ArrayList<>();
        HashMap<Integer, CourseInfo> retVal = new HashMap<Integer, CourseInfo>();
//        String retVal = "";
        //select the table
        String str = "";
        if(doc.getElementsByClass("dataentrytable").size() > 0) {
            for (Element e : doc.getElementsByClass("dataentrytable")) {
                //select each row

                CourseInfo c = null;
                for (Element t : e.child(0).children()) {
//                String str = e.get(0).child(0).child(1).html() + "\nFirst Row: " + t.size() + "\n";
//                for (int i = 0; i < t.size(); i++) {
//                    str += t.get(i).text() + "\n";
//                }
                    if (t.children().size() == 13) {
                        for (Element child : t.children()) {
                            temp.add(child.text().trim());
//                        retVal += child.text() + " | ";
                        }
//                    retVal += "\n";
                        if (!temp.get(0).contains("CRN")) {
                            c = new CourseInfo(temp);
                            retVal.put(c.getCrn(), c);
                        }
                        temp.clear();
                    } else if (t.children().size() == 10) {
                        //this is additional times
                        if (t.children().get(4).text().contains("* Additional Times *") && c != null) {
                            c.addTimes(t.children().get(5).text(), t.children().get(6).text(), t.children().get(7).text(), t.children().get(8).text());
                        }
                    }

                }
            }
        }
        //data table doesn't exist, there was an error
        else
        {
            if(doc.getElementsByClass("red_msg").size() > 0)
            {
                throw new Exception("No classes were found for this query");
            }
        }
        return retVal;
    }
}
