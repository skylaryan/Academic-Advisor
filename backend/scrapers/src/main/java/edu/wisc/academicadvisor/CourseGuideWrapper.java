/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author utkarsh
 */
public class CourseGuideWrapper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
           String s = null;

        try {
            
            Process p = Runtime.getRuntime().exec("python /Users/utkarsh/AcadAdvisor/backend/scrapers/src/main/python/CourseGuideScraper.py");
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            String outp="";
            while ((s = stdInput.readLine()) != null) {
                outp = outp +s;
            }
            
            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            System.out.println(outp);
            
            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
}
