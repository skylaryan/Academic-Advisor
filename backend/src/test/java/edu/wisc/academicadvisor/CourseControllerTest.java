package edu.wisc.academicadvisor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void noParamCoursesShouldReturnResults() throws Exception {

        this.mockMvc.perform(get("/courses")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    public void invalidParamCoursesShouldReturnNoResults() throws Exception {

        this.mockMvc.perform(get("/courses").param("breadth", "fake"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    public void goodCreditAmountShouldReturnResults() throws Exception {

        this.mockMvc.perform(get("/courses").param("credits", "3"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    public void badCreditAmountShouldReturnNoResults() throws Exception {

        this.mockMvc.perform(get("/courses").param("credits", "69"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    public void goodDepartmentShouldReturnResults() throws Exception {

        this.mockMvc.perform(get("/courses").param("department", "ZOOLOGY"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());

    }

    @Test
    public void invalidDepartmentShouldReturnNoResults() throws Exception {

        this.mockMvc.perform(get("/courses").param("department", "ROCKET SURGERY"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    public void goodNumberShouldReturnResults() throws Exception {

        this.mockMvc.perform(get("/courses").param("number", "200"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    public void badNumberShouldReturnNoResults() throws Exception {
        // should be noted that if the university starts offering courses at this high a level
        // this test will fail. I, however, do not see that happening
        this.mockMvc.perform(get("/courses").param("number", "69420666"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    public void goodBusyShouldReturnResults() throws Exception {
        this.mockMvc.perform(get("/courses").param("busy", "13:00-15:00//10:00-11:00//13:00-15:00////13:00-15:00"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test void alwaysBusyShouldReturnNoResults() throws Exception {
        this.mockMvc.perform(get("/courses").param("busy", "0:00-23:59//0:00-23:59//0:00-23:59//0:00-23:59//0:00-23:59"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test void goodTagsShouldReturnResults() throws Exception {
        this.mockMvc.perform(get("/courses").param("tags", "plant-epistemology-computer"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test void badTagsShouldReturnNoResults() throws Exception {
        this.mockMvc.perform(get("/courses").param("tags", "thisisabadstring-loremipsumamiright-andhisnameisjohncena"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

}





















