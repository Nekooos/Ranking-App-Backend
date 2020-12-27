package se.ranking.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @DisplayName("PATCH /competition/save Success")
    public void patchCompetition() throws Exception {
        Competition competition = testUtil.createXCompetitions(1, Collections.emptyList()).get(0);
        String json = "[{\"op\": \"replace\", \"path\": \"/name\", \"value\": \"patchedName\"}]";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonPatch jsonPatch = JsonPatch.fromJson(jsonNode);

        Mockito.when(competitionService.patchCompetition(jsonPatch, 1L))
                .thenReturn(competition);

        mockMvc.perform(patch("/competition/patch/{id}", 1L)
                .content(json)
                .contentType("application/json-patch+json"))
                .andExpect(status().is2xxSuccessful());
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
    public void saveCompetition() throws Exception {
        Competition competition = testUtil.createXCompetitions(1, Collections.emptyList()).get(0);
        Mockito.when(competitionService.save(competition))
                .thenReturn(competition);

        mockMvc.perform(post("/competition/save")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(competition))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("PUT /competition/save Success")
    public void editCompetition() throws Exception {
        Competition competition = testUtil.createXCompetitions(1, Collections.emptyList()).get(0);
        Mockito.when(competitionService.edit(1L, competition))
                .thenReturn(competition);

        mockMvc.perform(put("/competition/put/{id}", 1L)
                .content(objectMapper.writeValueAsString(competition))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("DELETE /competition/save Success")
    public void deleteCompetition() throws Exception {
        Mockito.doNothing().when(competitionService).delete(1L);

        mockMvc.perform(delete("/competition/delete/{id}", 1L))
                .andExpect(status().is2xxSuccessful());
    }
}
