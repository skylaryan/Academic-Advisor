package main.java.edu.wisc.academicadvisor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;
//import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class RmpScraperTest {

	@Test
	public void testRmpScraper() throws Exception {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		String[] scrapeArgs = new String[2];
		scrapeArgs[0] = "Elizabeth Kelly";
		scrapeArgs[1] = "Charles Dill";
		RmpScraper.main(scrapeArgs);
		String expectedOutput  = "{\"Elizabeth Kelly\":[3.3,3.6,[\"Tough Grader (72)\",\"Skip class? You won't pass. (55)\",\"LECTURE HEAVY (44)\",\"TEST HEAVY (20)\",\"LOTS OF HOMEWORK (16)\",\"Respected (15)\",\"Caring (15)\",\"GRADED BY FEW THINGS (13)\",\"Get ready to read (12)\",\"Clear grading criteria (10)\",\"Amazing lectures (9)\",\"ACCESSIBLE OUTSIDE CLASS (\",\"Inspirational (6)\",\"Hilarious (3)\",\"Participation matters (3)\",\"Gives good feedback (3)\"]],\"Charles Dill\":[4.2,2.0,[\"EXTRA CREDIT (6)\",\"Clear grading criteria (3)\",\"Skip class? You won't pass. (3)\",\"Amazing lectures (3)\",\"Inspirational (3)\",\"Respected (2)\",\"Participation matters (1)\",\"ACCESSIBLE OUTSIDE CLASS (1)\"]]}";
		assertEquals(expectedOutput, outContent.toString());
	}
}
