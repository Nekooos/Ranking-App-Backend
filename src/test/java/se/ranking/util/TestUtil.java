package se.ranking.util;

import se.ranking.model.Competition;
import se.ranking.model.Result;
import se.ranking.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtil {
    public List<User> createXUsers(int x, final List<Result> results) {
        return IntStream.range(0, x)
                .mapToObj(i -> createUser(i, results))
                .collect(Collectors.toList());
    }

    public User createUser(int i, List<Result> results) {
        User user = new User();
        user.setId((long) i);
        user.setFirstName("firstName"+i);
        user.setLastName("lastName"+i);
        user.setEmail("test"+i+"@mail.com");
        user.setPassword("password");
        user.setGender(i % 2 == 0 ? "Male" : "Female");
        user.setResults(results);
        return user;
    }

    public List<Competition> createXCompetitions(int x, List<User> users) {
        return IntStream.range(0, x)
                .mapToObj(i -> createCompetition(x, users))
                .collect(Collectors.toList());
    }

    public Competition createCompetition(int i, List<User> users) {
        Competition competition = new Competition();
        competition.setId((long) i);
        competition.setName("SM i Angered Simhall"+i);
        competition.setDate(LocalDate.now().toString());
        competition.setEndDate(datePlusOneDay());
        competition.setCountry("Sweden");
        competition.setEventType("SM");
        competition.setLocation("Angered Simhall");
        return competition;
    }

    private String datePlusOneDay() {
        String date = LocalDate.now().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(simpleDateFormat.parse(date));
        } catch(ParseException e) {
            // Log
        }
        c.add(Calendar.DATE, 1);
        return simpleDateFormat.format(c.getTime());
    }

    public List<Result> createXResults(int x, final User user) {
        return IntStream.range(0, x)
                .mapToObj(i -> createResult(i, user))
                .collect(Collectors.toList());
    }

    public Result createResult(int x, User user) {
        Result result = new Result();
        result.setId((long) x);
        result.setDiscipline("STA");
        result.setAnnouncedPerformance(randomTimeAndPoints());
        result.setCard(card(x));
        result.setPoints(Integer.parseInt(randomTimeAndPoints()));
        result.setUser(user);
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
