package edu.wisc.academicadvisor.controller;

import edu.wisc.academicadvisor.scraper.CourseGuide;
import edu.wisc.academicadvisor.scraper.GradeDistribution;
import edu.wisc.academicadvisor.scraper.RateMyProfessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ScraperController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/scrape")
    public @ResponseBody void courses(@RequestParam(value="process", defaultValue="") String process) {
        if (process.isEmpty()) {
            try {
                scrapeCourseGuide();
                scrapeGradeDistribution();
                scrapeRateMyProfessor();
                mergeScrapedTables();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            String[] processes = process.split("-");
            // TODO: subsect of processes
        }
    }

    private void scrapeCourseGuide() throws IOException {
        CourseGuide courseGuide = new CourseGuide();
    }

    private void scrapeGradeDistribution() throws IOException {
        GradeDistribution gradeDistribution = new GradeDistribution();
        gradeDistribution.GradeDistribution();
    }

    private void scrapeRateMyProfessor() throws IOException {
        RateMyProfessor rateMyProfessor = new RateMyProfessor();
        String[] tmp = {"Michael Morrow"}; // TODO
        rateMyProfessor.RateMyProfessor(tmp);
    }

    private void mergeScrapedTables() {
        // TODO
    }
}
