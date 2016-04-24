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
    public String parseTable(String html)
    {
        Document doc = Jsoup.parse(html);
        ArrayList<String> temp = new ArrayList<>();
//        HashMap<Integer, CourseInfo> retVal = new HashMap<Integer, CourseInfo>();
        String retVal = "";
        //select the table
        String str = "";
        for (Element e : doc.getElementsByClass("dataentrytable")) {
            //select each row
            for(Element t : e.child(0).children()) {
//                String str = e.get(0).child(0).child(1).html() + "\nFirst Row: " + t.size() + "\n";
//                for (int i = 0; i < t.size(); i++) {
//                    str += t.get(i).text() + "\n";
//                }
                if(t.children().size() == 13)
                {
                    for(Element child : t.children())
                    {
//                        temp.add(child.text());
                        retVal += child.text() + " | ";
                    }
                    retVal += "\n";
//                    CourseInfo c = new CourseInfo(temp);
//                    retVal.put(c.getCrn(), c);
                }

            }
        }
        return retVal;
    }
}
