package se.ranking.controller;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.ranking.model.Competition;
import se.ranking.service.CompetitionServiceImpl;
import se.ranking.util.TestUtil;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CompetitionControllerTest {
    @MockBean
    private CompetitionServiceImpl competitionService;
    private TestUtil testUtil;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void Setup(){
        testUtil = new TestUtil();
    }

    @Test
    @DisplayName("GET /competition/all Success")
    public void findAllCompetitionsTest() throws Exception {
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
}
