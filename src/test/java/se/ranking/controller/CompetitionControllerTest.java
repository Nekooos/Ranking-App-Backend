package se.ranking.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.ranking.model.Competition;
import se.ranking.service.CompetitionServiceImpl;
import se.ranking.util.TestUtil;

import java.util.Collections;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompetitionControllerTest {
    @InjectMocks
    private CompetitionController competitionController;
    @Mock
    private CompetitionServiceImpl competitionService;
    private TestUtil testUtil;

    @BeforeAll
    public void Setup(){
        MockitoAnnotations.initMocks(this);
        competitionController = new CompetitionController();
        testUtil = new TestUtil();
    }

    @Test
    public void findAllCompetitionsTest() {
        Mockito.when(competitionService.findAll()).thenReturn(testUtil.createXCompetitions(5, Collections.emptyList()));
        List<Competition> competitions = competitionService.findAll();
        Competition expectedCompetition = testUtil.createCompetition(1, Collections.EMPTY_LIST);
        Assertions.assertEquals(5, competitions.size());
        Assertions.assertEquals("Sweden", competitions.get(0).getCountry());
    }
}
