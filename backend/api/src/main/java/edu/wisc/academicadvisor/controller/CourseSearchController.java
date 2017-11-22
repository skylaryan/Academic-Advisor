package edu.wisc.academicadvisor.controller;

import edu.wisc.academicadvisor.model.Course;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CourseSearchController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/courses")
    @ResponseBody
    public List<Course> courses(@RequestParam(value="breadth", defaultValue="") String breadth) {
        List<Map<String, Object>> rows;
        List<Course> courses = new ArrayList<>();
        try {
            if (!breadth.isEmpty()) {
                breadth = breadth.replace("-", "' OR '"); // .replace("%20", " ")
                rows = jdbcTemplate.queryForList("SELECT * FROM mergedcourses WHERE breadth=" + breadth + ";");
            } else rows = jdbcTemplate.queryForList("SELECT * FROM mergedcourses;");

            for (Map row : rows) {
                Course course = new Course((String)row.get("course"),
                                            (Integer)row.get("section"),
                                            (String)row.get("title"),
                                            (Integer)row.get("numCredits"),
                                            ((String)row.get("breadth")).split("|"),
                                            (String)row.get("professor"),
                                            (Double)row.get("professorRating"),
                                            (String)row.get("description"),
                                            ((String)row.get("schedule")).split("|")); // TODO: empty?
                courses.add(course);
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        return courses;
    }
}
