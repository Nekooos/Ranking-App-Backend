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
import se.ranking.service.CompetitionServiceImpl;
import se.ranking.util.TestUtil;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompetitionControllerTest {
    @MockBean
    private CompetitionServiceImpl competitionService;
    private TestUtil testUtil;
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void Setup(){
        testUtil = new TestUtil();
    }

    @Test
    @DisplayName("GET /competition/all")
    public void findAllCompetitionsTest() throws Exception {
        Mockito.when(competitionService.findAll())
                .thenReturn(testUtil.createXCompetitions(5, Collections.emptyList()));

        mockMvc.perform(get("/competition/all"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[0].id").value(1));

    }
}
