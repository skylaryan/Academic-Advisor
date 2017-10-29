/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
}
public class rmpScraper {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        int currOffset = 0;
        PrintWriter writer = new PrintWriter("rmplinks.txt", "UTF-8");
        while(currOffset <= 3960) {
            String url = "http://www.ratemyprofessors.com/search.jsp?query=university+of+wisconsin+madison&queryoption=HEADER&stateselect=&country=&dept=&queryBy=&facetSearch=&schoolName=&offset=" + Integer.toString(currOffset) + "&max=20";
            
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");
            
            for(Element link : links) {
                if(link.text().contains("PROFESSOR"))
                    writer.println(link.attr("abs:href"));
            }
            currOffset += 20;
        }
        writer.close();
        ArrayList<Professor> arr = new ArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader("rmplinks.txt"))) {
            String line;
            while ((line = br.readLine()) != null)
            {
                Document doc = Jsoup.connect(line).get();
                Professor s = new Professor();
                Elements x = doc.getElementsByClass("profname");
                Elements q = doc.getElementsByClass("grade");
                List<String> y = new LinkedList<String>();
                List<String> z = new LinkedList<String>();
                
                y = x.eachText();
                z = q.eachText();
                for(int i=0;i<y.size();i++)
                {
                    Professor temp = new Professor();
                    temp.name = y.get(i);
                    temp.rating = Float.parseFloat(z.get(i));
                    arr.add(temp);
                    System.out.println(temp.name+" "+temp.rating);
                }
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
