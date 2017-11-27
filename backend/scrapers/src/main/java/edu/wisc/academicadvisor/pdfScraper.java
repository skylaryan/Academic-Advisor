import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class pdfScraper {

   public static void main(String args[]) throws IOException {
	  
	  //generate the array of relevant grade distribution files 
	  File [] files = new File[6];
	  files [0] = new File("F:/UW-Madison/Stats_distribs_2015-2016Fall-164-193.pdf");
	  files [1] = new File("F:/UW-Madison/Stats_distribs_2015-2016Fall-194-223.pdf");
	  files [2] = new File("F:/UW-Madison/Stats_distribs_2015-2016Fall-224-253.pdf");
	  files [3] = new File("F:/UW-Madison/Stats_distribs_2015-2016Fall-254-283.pdf");
	  files [4] = new File("F:/UW-Madison/Stats_distribs_2015-2016Fall-284-313.pdf");
	  files [5] = new File("F:/UW-Madison/Stats_distribs_2015-2016Fall-314-339.pdf");
	  
	  
      Map<String, double []> gradeDistribution = new HashMap<String, double []>();
      String totalLines[] = new String [10000];
      ArrayList<String> list = new ArrayList<String>();
	  
	  for(int count = 0; count < 6; count++){
		 
    	  System.out.println("This is the " + (count + 1) + " times beginning.....................................................................");

	      //Loading an existing document
		  PDDocument document = PDDocument.load(files[count]);
	
	      //Instantiate PDFTextStripper class
	      PDFTextStripper pdfStripper = new PDFTextStripper();
	
	      //Retrieving text from PDF document
	      String text = pdfStripper.getText(document);	      
	      
	      //split the rest into an String array by each line
	      String lines [] = text.split("\\r?\\n");
	      
	     
	      for (int i = lines.length - 1; i >= 0; i--){
	    	  if (lines [i].startsWith("Course Total")){
	    		  String tempStr = lines[i - 1];
	    		  lines [i] = String.join(" ", lines [i], tempStr.substring(tempStr.length() - 3));
	    	  }
	      }
	            
	      String departName = "";
	      for (int i = 0; i < lines.length; i++){
	    	  if(lines[i].equals("A AB B BC C D F S U CR N P I NW NR Other"))
	    		  departName = lines[i + 1];
	    	  
	    	  lines [i] = String.join(" departmentName ",  lines [i], departName );
	    	  
	    	  if(lines[i].equals("Fall 2015-2016 University of Wisconsin - Madison"))
	    		  departName = "";
	    	  
	    	  lines[i] = lines[i].replaceAll("\\*+", "=== ");
	      }
	      
	      //eliminate different sections in one course but keep section #1 to get the course number
	      for(int i = 0; i < lines.length; i++){
	    	  String temp []= lines[i].split(" ");
	    	  if(lines[i].startsWith("001")){
	    		  if(temp[temp.length - 4].isEmpty())
	    			   lines[i] = null;
	    		  
	    	  }else if((lines[i].startsWith("001") == false) && (lines[i].startsWith("Course Total") == false))
	    		  lines[i] = null;    	 
	      }	     
	      	
	      //ArrayList<String> list1 = new ArrayList<String>();
	      for (String a : lines){
	          if (a != null)
	              list.add(a);
	      }
	    	      
	      /*
	      for(int i = 0; i < lines.length; i++){
	    	  System.out.println(totalLines[i]);
	      }
    	  System.out.println("This is the " + (count + 1) + " end.................................................................................");
    	  */
	      
	      document.close();
	  }
    	  
      totalLines = list.toArray(new String[list.size()]);
      
      for(int i = 0; i < totalLines.length; i++){
    	  System.out.println(totalLines[i]);
      }
    	  
      for(int i = 0; i < totalLines.length; i++){
    	  
    		  double grade[] = new double [7];
        	  String courseName;
        	  
        	  System.out.println(totalLines[i]);
        	  
        	  //if the line starts with "001"
    		  if(totalLines[i].startsWith("001")){
    			  //course name 
    			  String temp []= totalLines[i].split(" departmentName ");
    			  String courseDepartment = temp[1];
    			  
    			  temp = totalLines[i].split(".   ");
    			  String courseNum = temp[temp.length - 1].substring(0, 3);
    			  courseName = courseDepartment + " " + courseNum;
    			  
    			  System.out.println(courseName);
    			  
    			  //course grade distribution
    			  temp = totalLines[i].split(" ");
    			  int counter = 9;
    			  int index = 0;
    			  
    			  for(int j = 2; j < counter; j++){
    				  
    				  if(temp[j].equals("===")){
    					  for(int k = 0; k < grade.length; k++){
    						  grade[k] = 0;
    					  }
    				  }else if(temp[j].startsWith(".")){
    					  grade[index] = 0;
    				  }else if(temp[j].matches("")){
    					  for(int k = 0; k < grade.length; k++){
    						  grade[k] = 0;
    					  }
    				  }else{
    					  grade [index] = Double.valueOf(temp[j]);
    				  }
    				   
    				  System.out.println(grade[index]);
    				  index++;
    			   }
    			  
    			  //if the line starts with "Course Total"
    		  }else{
    			  
    			  //course name
    			  String temp []= totalLines[i].split(" departmentName ");
    			  
    			  String departmentName = temp[1];
    			  String courseNum = temp[0].substring(temp[0].length() - 3);
    			  courseName = departmentName + " " + courseNum;
    			  System.out.println(courseName);
    			 
    			  //course grade
    			  temp = totalLines[i].split(" ");
    			  int counter = 9;
    			  int index = 0;
    			  
    			  for(int j = 3; j < counter; j++){
    				  
    				  if(temp[j].equals("===")){
    					  for(int k = 0; k < grade.length; k++){
    						  grade[k] = 0;
    					  }
    					  break;
    				  }else if(temp[j].startsWith(".")){
    					  grade[index] = 0;
    				  }else if(temp[j].matches("")){
    					  counter++;
    					  index--;
    				  }else{
    					  grade [index] = Double.valueOf(temp[j]);
    				  }
    				   
    				   System.out.println(grade[index]);
    				   index++;
    			   }
    			  
    		  }
    	  }
    	  
    
	  }
  
}