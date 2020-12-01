package se.ranking.util;

import org.springframework.beans.factory.annotation.Autowired;
import se.ranking.model.Competition;
import se.ranking.model.Result;
import se.ranking.model.User;
import se.ranking.repository.CompetitionRepository;
import se.ranking.repository.ResultRepository;
import se.ranking.repository.UserRepository;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.IntStream;

public class TestUtil {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CompetitionRepository competitionRepository;

    @Autowired
    ResultRepository resultRepository;

    public void createXusers(int x) {
        IntStream.range(0, x)
                .forEach(i -> userRepository.save(createUser(i)));
    }

    public User createUser(int i) {
        User user = new User();
        user.setFirstName("firstName"+i);
        user.setLastName("lastName"+i);
        user.setEmail("test"+i+"@mail.com");
        user.setPassword("password");
        user.setGender(i % 2 == 0 ? "Male" : "Female");
        return user;
    }

    public void createXEvents(int x) {
        IntStream.range(0, x)
                .forEach(i -> competitionRepository.save(createEvent(i)));
    }

    public Competition createEvent(int i) {
        Competition competition = new Competition();
        competition.setDate(LocalDate.now().toString());
        competition.setDicipline("STA"+i);
        competition.setUsers(userRepository.findAll());
        return competition;
    }

    public void createXResults(int x) {
        IntStream.range(0, x)
                .forEach(i -> resultRepository.save(createResult(i)));
    }

    public Result createResult(int x) {
        Result result = new Result();
        result.setDiscipline("STA");
        result.setAnnouncedPerformance(randomTimeAndPoints());
        result.setCard(card(x));
        result.setPoints(Integer.parseInt(randomTimeAndPoints()));
        result.setUser(userRepository.findById((long) x).get());
        result.setReportedPerformance(randomTimeAndPoints());
        return result;
    }

    private String randomTimeAndPoints() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100)+1);
    }

    private String card(int i) {
        if(i % 3==0)
            return "red";
        else if(i % 3 == 1) {
            return "yellow";
        }
        else {
            return "white";
        }
    }

}
