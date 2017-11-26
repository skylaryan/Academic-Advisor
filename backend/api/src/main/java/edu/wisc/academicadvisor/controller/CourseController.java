package edu.wisc.academicadvisor.controller;

import edu.wisc.academicadvisor.model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    List<Map<String, Object>> rows;
    List<Course> courses = new ArrayList<>();

    @RequestMapping("/courses")
    public @ResponseBody List<Course> courses(@RequestParam(value="breadth", defaultValue="") String breadth,
                                              @RequestParam(value="credits", defaultValue="0") int numCredits,
                                              @RequestParam(value="department", defaultValue="") String department,
                                              @RequestParam(value="number", defaultValue="0") int number,
                                              @RequestParam(value="busy", defaultValue="") String busy,
                                              @RequestParam(value="tags", defaultValue="") String tags) {
        try {
            if (!breadth.isEmpty()) {
                breadth = breadth.replace("-", "' OR '");
                rows = jdbcTemplate.queryForList("SELECT * FROM mergedcourses WHERE breadth='" + breadth + "'");
            } else rows = jdbcTemplate.queryForList("SELECT * FROM mergedcourses");

            String[] tmp = {"Natural Sciences", "Natural Science"};

            for (Map row : rows) {
                courses.add(new Course((String)row.get("course"),
                        (Integer)row.get("section"),
                        (String)row.get("title"),
                        (Integer)row.get("numCredits"),
                        tmp,
                        (String)row.get("professor"),
                        (Double)row.get("professorRating"),
                        //(String)row.get("gradeHistory"),
                        (String)row.get("description"),
                        ((String)row.get("schedule")).split("|"))); // TODO: empty? i.e. || vs | |
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        return courses;
    }
}
