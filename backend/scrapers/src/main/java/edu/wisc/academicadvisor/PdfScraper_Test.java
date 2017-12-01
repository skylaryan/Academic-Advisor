package main.java.edu.wisc.academicadvisor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;


public class PdfScraper_Test {

	@Test
	public void test() throws FileNotFoundException, IOException  {
		
		File [] files = new File[1];
		files [0] = new File("/Users/toconnell/Dropbox/development/school/cs506/academicadvisor/backend/scrapers/src/main/java/edu/wisc/academicadvisor/Stats_distribs_2015-2016Fall-164-193.pdf");		//files [1] = new File("F:/UW-Madison/Stats_distribs_2015-2016Fall/Stats_distribs_2015-2016Fall-test-files/Stats_distribs_2015-2016Fall-164-193-2-2.pdf");
		
		String [] expectedOutput = {"20", "120", "23", "43.5","Course", "Total", "First", "COM", "CHEM"};

		PdfScraper pdfScraper = new PdfScraper();
		String [] rawOutput = pdfScraper.getRawData(files);
		
		for(int i = 0; i < rawOutput.length; i++ ){
			if(rawOutput[i] == null) System.out.println("Wrong");
			System.out.println(rawOutput[i]);
		}

		String [] realOutput = new String[10000000];
		
		int index = 0;
		
		/*
		for(int i = 0; i < rawOutput.length; i++){
			String [] temp = rawOutput[i].split(" ");
			
			for (int j = 0; j < temp.length; j++){
				if (temp[j] == null) System.out.println("i: " + i + " j: " + j);
				realOutput[index] = temp[j];
				index++;
			}
			
			System.out.println(realOutput[index]);
		}
		*/
		
		List<String> toFind = Arrays.asList(expectedOutput);
		List<String> toCheck = Arrays.asList(rawOutput);
		
		boolean[] asserted = new boolean[toFind.size()];
		for (int i = 0; i < toFind.size(); i++) {
			for (int j = 0; j < toCheck.size(); j++) {
				if (toCheck.get(j).contains(toFind.get(i))) {
					//System.out.println("Wrong");
					asserted[i] = true;

				}
			}
			/*if (!toCheck.contains(toFind.get(i))) {
				System.out.println("not found: " + toFind.get(i));
				for (int j = 0; j < toCheck.size(); j++) {
					System.out.println(toCheck.get(j));
				}
				assert(false);
			}*/
		}
		boolean ret = true;
		for (int i = 0; i < asserted.length; i++) if (!asserted[i]) ret = false;
		
		assert(ret);
		//assert(false);
		//assert(true);
		
		//assert(Arrays.asList(rawOutput).containsAll(Arrays.asList(expectedOutput)));
		
		/*boolean[] contains = new boolean[expectedOutput.length];
		int i = 0;
		 while (realOutput[i] != null) {
			 for (int j = 0; j < expectedOutput.length; j++) {
				 if (realOutput[i] == null) System.out.println("i: " + i + " rO.len: " + realOutput.length + " eO.len: " + expectedOutput.length);
				 if (expectedOutput[j] == null) System.out.println("j: " + j);
				 
		 		if (realOutput[i].equals(expectedOutput[j])) {
		 			contains[j] = true;
		 		}
			 }
			 contains = new boolean[expectedOutput.length];
			 i++;
		 }
		 for (int j = 0; j < contains.length; j++) if (!contains[j]) assert(false);
		 assert(true);*/
	}

}
