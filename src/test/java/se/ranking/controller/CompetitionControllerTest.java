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
import se.ranking.model.Competition;
import se.ranking.service.CompetitionServiceImpl;
import se.ranking.util.TestUtil;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CompetitionControllerTest {
    @MockBean
    private CompetitionServiceImpl competitionService;
    private TestUtil testUtil;
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void Setup(){
        testUtil = new TestUtil();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("GET /competition/all Success")
    public void getAll() throws Exception {
        List<Competition> competitions = testUtil.createXCompetitions(5, Collections.emptyList());
        Mockito.when(competitionService.findAll())
                .thenReturn(competitions);

        mockMvc.perform(get("/competition/all"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[4].id").value(5));
    }

    @Test
    @DisplayName("GET /competition/1 Success")
    public void getById() throws Exception {
        Competition competition = testUtil.createXCompetitions(1, Collections.emptyList()).get(0);
        Mockito.when(competitionService.findById(1L))
                .thenReturn(competition);

        mockMvc.perform(get("/competition/{id}", 1L))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST /competition/save Success")
    public void save() throws Exception {
        Competition competition = testUtil.createXCompetitions(1, Collections.emptyList()).get(0);
        Mockito.when(competitionService.save(competition))
                .thenAnswer(i -> competition);

        mockMvc.perform(post("/competition/save")
                .content(objectMapper.writeValueAsString(competition))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }
}
