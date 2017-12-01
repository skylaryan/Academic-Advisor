/*package main.java.edu.wisc.academicadvisor;

import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.lang.*;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RmpScraper {
    public static String main(String[] args) throws IOException
    {
        
        
        String[] arr = new String[2];
        arr[0]=args[0];
        arr[1]=args[1];
        JSONObject json = new JSONObject();
        for(int i=0;i<arr.length;i++)
        {
            String temp="";
            for(int j=0;j<arr[i].length();j++)
            {
                if(arr[i].charAt(j)==' ')
                    temp+='+';
                else
                    temp+=arr[i].charAt(j);
            }
            String url = "http://www.ratemyprofessors.com/search.jsp?query="+temp+"+wisconsin+madison";
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");
            String proflink = null;
            for(Element link : links) {
                if(link.text().contains("PROFESSOR"))
                {
                    proflink = link.attr("abs:href");
                }
            }
            JSONArray profarray = new JSONArray();
            doc = Jsoup.connect(proflink).get();
            
            // getElements by class returns an Elements object with information of each class with the specified name in the HTML file
            Elements pname = doc.getElementsByClass("profname");
            Elements rating = doc.getElementsByClass("grade"); // difficulty and professor rating both have class=grade
            Elements tags = doc.getElementsByClass("tag-box-choosetags");
            
            List<String> pn = new LinkedList<String>();
            List<String> rat = new LinkedList<String>();
            List<String> proftag = new LinkedList<String>();
            
            
            // eachText returns a list of strings of the content of the Elements object
            pn = pname.eachText();
            rat = rating.eachText();
            proftag = tags.eachText();
            
            profarray.add(Float.parseFloat(rat.get(0)));
            profarray.add(Float.parseFloat(rat.get(2)));
            profarray.add(proftag);
            json.put(pn.get(0),profarray);
            
        }
        
        StringWriter out = new StringWriter();
        json.writeJSONString(out);
        
        String jsonText = out.toString();
        return jsonText;
        
    }
    
}

*/