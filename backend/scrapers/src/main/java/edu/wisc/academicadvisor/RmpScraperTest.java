/*package main.java.edu.wisc.academicadvisor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;
//import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class RmpScraperTest {

	
//	ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
/*	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}
*/	/*
	@Test
	public void testRmpScraper() throws Exception {
//		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(outContent));
		String[] scrapeArgs = new String[2];
//		System.out.println("sorry i'm reaching over you Skylar 1");
		scrapeArgs[0] = "Jignesh Patel";
		scrapeArgs[1] = "Sally Peterson";
//		System.out.println("sorry i'm reaching over you Skylar 2");
		String output = RmpScraper.main(scrapeArgs);
//		System.out.println("sorry i'm reaching over you Skylar 3");
		String expectedOutput  = "{\"Jignesh Patel\":[2.6,4.1,[]],\"Sally Peterson\":[3.2,2.9,[]]}";
//		System.out.println("sorry i'm reaching over you Skylar 4");
//		System.out.println(output);
		assertEquals(expectedOutput, output);
//		System.out.println("sorry i'm reaching over you Skylar");
//		System.out.println("THIS IS WHAT WE WANT" + outContent.toString());
	}
}
*/