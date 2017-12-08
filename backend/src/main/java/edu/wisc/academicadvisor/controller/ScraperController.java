package edu.wisc.academicadvisor.controller;

import edu.wisc.academicadvisor.scraper.CourseGuide;
import edu.wisc.academicadvisor.scraper.GradeDistribution;
import edu.wisc.academicadvisor.scraper.RateMyProfessor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
public class ScraperController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/scrape")
    public @ResponseBody boolean courses(@RequestParam(value="process", defaultValue="") String process) {
        if (process.isEmpty()) {
            try {
                if (scrapeCourseGuide()) {
                    if (scrapeGradeDistribution() && scrapeRateMyProfessor()) {
                        return mergeScrapedTables();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (org.json.simple.parser.ParseException ex) {
                System.out.println("scraper fail, trying again");
                ex.printStackTrace();
                courses(process);
                ex.printStackTrace();
            }

        } else {
            String[] processes = process.split("-");
            // TODO: subsect of processes
        }
        return false;
    }

    private boolean scrapeCourseGuide() throws IOException, ParseException {
        jdbcTemplate.execute("DELETE FROM mergedcourses;");
        //jdbcTemplate.execute("DELETE FROM class;");
        System.out.println("create CourseGuide()");
        CourseGuide courseGuide = new CourseGuide();
        System.out.println("getJsonString()");
        String courses = courseGuide.getJsonString();
        System.out.println(courses);
        JSONParser jsonParser = new JSONParser();
        JSONArray listOfCourses = (JSONArray) jsonParser.parse(courses);
        System.out.println(listOfCourses.toJSONString());
        for (int i = 0; i < listOfCourses.size(); i++) { // each course
            JSONArray course = (JSONArray) listOfCourses.get(i);
            for (int j = 0; j < course.size(); j++) {
                System.out.println(course.get(j));
            }
            System.out.println(course.get(4).toString());
            if (!course.get(4).toString().contains("2017")) continue;
            String[] description = course.get(5).toString().split("Pre-Reqs: ");
            jdbcTemplate.execute("INSERT INTO mergedcourses (course, courseNum, title, numCredits, breadth, description, prereqs) VALUES ('" + course.get(0).toString().replaceAll("('|\n|\t)","") + "'," + course.get(1).toString().replaceAll("('|\n|\t)","").replace("Cross-Listed","") + ",'" + course.get(2).toString().toString().replaceAll("('|\n|\t)+","") + "'," + course.get(3).toString().replaceAll("('|\n|\t)","") + ",'Natural Sciences','" + description[0].replaceAll("('|\n|\t)","").replace("Course Description: ","").replaceAll("('|\n|\t)'","") + "','" + description[1].replaceAll("'('|\n|\t)","") + "');");
            for (int j = 6; j < course.size() - 3;) {
                System.out.println("section: " + course.get(j));
                System.out.println("location: " + course.get(j+2));
                System.out.println("schedule: " + course.get(j+3));
                /*jdbcTemplate.execute("INSERT INTO class (course, courseNum, section, location, schedule, " +
                        "professor) VALUES ('" + course.get(0) + "'," + course.get(1) + ",'" + course.get(j) + "','" +
                        course.get(j+1) + "'," + course.get(j+2) + "'');");*/
                if (course.size() > j + 4 && course.get(j+4).equals('\u00A0')) j++;
                j += 4;
            }

        }
        return true;
    }

    private boolean scrapeGradeDistribution() throws IOException {
        /*GradeDistribution gradeDistribution = new GradeDistribution();
        Map<String, double[]> distributionMap = gradeDistribution.GradeDistribution();
        Set<String> courses = distributionMap.keySet();
        double[] course = new double[distributionMap.keySet().size()];
        jdbcTemplate.execute("DELETE FROM gradeDis;");
        int i = 0;
        for (String s : distributionMap.keySet()) {
            try {
                String out = Arrays.toString(distributionMap.get(s));
                out = out.replace(" ", "").replace("[", "").replace("]", "");
                String[] output = out.split(",");
                for (int j = 0; j < output.length; j++) {
                    course[j] = Double.parseDouble(output[j]);
                }

                String[] tmp = s.split(" ");
                try {
                    Integer.parseInt(tmp[tmp.length-1]);
                } catch (Exception ex) {
                    System.out.println("Cannot parse " + tmp[tmp.length-1]);
                    continue;
                }

                jdbcTemplate.execute("INSERT INTO gradeDis (course, avg, percA, percAB, percB, percBC," +
                        "percC, percD, percF) VALUES ('" + s + "'," + course[0] + "," + course[1] +
                        "," + course[2] + "," + course[3] + "," + course[4] + "," + course[5] + "," + course[6] + "," +
                        course[7] + ");");
                i++;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }*/
        return true;
    }

    private boolean scrapeRateMyProfessor() throws IOException, ParseException {
        /*jdbcTemplate.execute("DELETE FROM professor;"); // TODO
        RateMyProfessor rateMyProfessor = new RateMyProfessor();
        List<String> professors = jdbcTemplate.queryForList("SELECT professor FROM class;", String.class);
        Object[] tmp = professors.toArray();
        String[] list = new String[tmp.length];
        for (int i = 0; i < tmp.length; i++) list[i] = (String)tmp[i];
        String json = rateMyProfessor.RateMyProfessor(list);
        //System.out.println(json);
        JSONParser jsonParser = new JSONParser();
        JSONArray listOfProfessors = (JSONArray) jsonParser.parse(json);
        //System.out.println(listOfProfessors.toJSONString());
        for (int i = 0; i < listOfProfessors.size(); i++) { // each course
            JSONArray professor = (JSONArray) listOfProfessors.get(i);
            if (professor.size() == 4) {
                JSONArray tags = (JSONArray) professor.get(3);
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < tags.size() - 1; j++) {
                    sb.append(tags.get(j).toString().replace("'","") + " ");
                }
                sb.append(tags.get(tags.size() - 1).toString().replace("'",""));

                jdbcTemplate.execute("INSERT INTO professor (profName, rating, easiness, tags) VALUES ('" +
                        professor.get(0) + "'," + professor.get(1) + "," + professor.get(2) + ",'" + sb.toString() + "')");
            } else System.out.println("professor.size() is not as expected: " + professor.size());
        }*/
        return true;
    }

    private boolean mergeScrapedTables() {
        // TODO
        return true;
    }
}
