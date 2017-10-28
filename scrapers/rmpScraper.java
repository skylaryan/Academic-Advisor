import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class rmpScraper {
	public static void main(String[] args) throws FileNotFoundException, IOException {

		int currOffset = 0;
		PrintWriter writer = new PrintWriter("rmplinks.txt", "UTF-8");
		while(currOffset <= 3960) {
			String url = "http://www.ratemyprofessors.com/search.jsp?query=university+of+wisconsin+madison&queryoption=HEADER&stateselect=&country=&dept=&queryBy=&facetSearch=&schoolName=&offset=" + Integer.toString(currOffset) + "&max=20";
			
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("a[href]:contains(ShowRatings)"); 

			for(Element link : links) {
			 	writer.println(link.attr("abs:href"));
			}



			currOffset += 20;
		}
		writer.close();



	}
}
