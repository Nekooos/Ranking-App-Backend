package se.ranking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.ranking.model.Card;
import se.ranking.model.Discipline;
import se.ranking.model.Result;
import se.ranking.model.UserDto;
import se.ranking.service.ResultService;
import se.ranking.service.UserService;
import se.ranking.util.TestUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@SpringBootTest
@AutoConfigureMockMvc
public class ResultControllerTest {
    @MockBean
    private ResultService resultService;
    @Autowired
    private MockMvc mockMvc;

    private TestUtil testUtil;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        testUtil = new TestUtil();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("POST result/save throws MethodArgumentNotValidException")
    public void saveNotValidResult() throws Exception {
        Result result = testUtil.createCustomResult(1L, Discipline.STA, Card.WHITE, null, "4:45.0", 78.0, "2021-06-01");

        when(resultService.save(result))
                .thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(post("/result/save")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(result))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message").value("Form validation failed"))
                .andExpect(jsonPath("$.fieldErrors").isArray())
                .andExpect(jsonPath("$.fieldErrors[?(@.field == \"announcedPerformance\")]").exists())
                .andExpect(jsonPath("$.fieldErrors[?(@.objectName == \"result\")]").exists())
                .andExpect(jsonPath("$.fieldErrors[?(@.errorMessage == \"Announced performance is required\")]").exists())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("POST result/save")
    public void saveValidResult() throws Exception {
        Result result = testUtil.createCustomResult(1L, Discipline.STA, Card.WHITE, "4.45.0", "4:45.0", 78.0, "2021-06-01");

        when(resultService.save(any()))
                .thenAnswer(i -> result);

        mockMvc.perform(post("/result/save")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(result))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }
}
