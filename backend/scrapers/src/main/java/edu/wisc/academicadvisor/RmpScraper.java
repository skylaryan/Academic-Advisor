import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class Professor
{
    String name;
    double rating;
    double diff;
    List< String > tag;
}
public class RmpScraper {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        
        int currOffset = 0;
        PrintWriter writer = new PrintWriter("rmplinks.txt", "UTF-8");
        // In this loop, we query ratemyprofessors for UW-Madison professors and put a link to each professors page in .txt called rmplinks.txt
        String url = "http://www.ratemyprofessors.com/search.jsp?query=university+of+wisconsin+madison&queryoption=HEADER&stateselect=&country=&dept=&queryBy=&facetSearch=&schoolName=&offset=" + Integer.toString(currOffset) + "&max=20";
            
        Document docScrapeLinks = Jsoup.connect(url).get();
        Elements links = docScrapeLinks.select("a[href]");
        
	Elements resultCount = docScrapeLinks.getElementsByClass("result-count");
	String[] parsed = resultCount.eachText().get(1).split(" ");
	int maxPage = Integer.parseInt(parsed[parsed.length - 2]);        
        
        while(currOffset <= maxPage) {
            
            
            for(Element link : links) {
                if(link.text().contains("PROFESSOR"))
                    writer.println(link.attr("abs:href"));
            }
            currOffset += 20;
        }
        writer.close();
        
        // for each link in rmplinks.txt, we extract information about each professor
        ArrayList<Professor> arr = new ArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader("rmplinks.txt"))) {
            String line;
            while ((line = br.readLine()) != null)
            {
                Document doc = Jsoup.connect(line).get();
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
                Professor temp = new Professor();
                temp.name = pn.get(0);
                temp.rating = Float.parseFloat(rat.get(0));
                temp.diff = Float.parseFloat(rat.get(2));
                temp.tag = proftag;
                arr.add(temp);
                System.out.println(temp.name+" "+temp.rating+" "+temp.diff);
                for(int j=0;j<proftag.size();j++)
                {
                    System.out.println(temp.tag.get(j));
                }
                System.out.println();
            }
            /*
             for(int i=0;i<arr.size();i++)
             {
             System.out.println(arr.get(i).name+" "+arr.get(i).rating);
             }
             */
            
        }
        
        
    }
    
}
