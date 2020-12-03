package se.ranking.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.ranking.model.Competition;
import se.ranking.service.CompetitionService;
import se.ranking.util.TestUtil;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompetitionControllerTest {
    @Autowired
    private CompetitionService competitionService;
    private TestUtil testUtil;

    @BeforeAll
    public void Setup(){
        testUtil = new TestUtil();


    }

    @Test
    public void eventTest() {
        List<Competition> competitions = competitionService.findAll();
    }
}
