package se.ranking.util;

import se.ranking.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtil {
    public List<RegisteredUser> createXUsers(int x, final List<Result> results) {
        return IntStream.range(0, x)
                .mapToObj(i -> createUser(i, results))
                .collect(Collectors.toList());
    }

    public RegisteredUser createUser(int i, List<Result> results) {
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setId((long) i);
        registeredUser.setFirstName("firstName"+i);
        registeredUser.setLastName("lastName"+i);
        registeredUser.setEmail("test"+i+"@mail.com");
        registeredUser.setPassword("password");
        registeredUser.setGender(i % 2 == 0 ? "Male" : "Female");
        registeredUser.setResults(results);
        return registeredUser;
    }

    public List<Competition> createXCompetitions(int x, List<RegisteredUser> registeredUsers) {
        return IntStream.range(0, x)
                .mapToObj(i -> createCompetition(i+1, registeredUsers))
                .collect(Collectors.toList());
    }

    public Competition createCompetition(int i, List<RegisteredUser> registeredUsers) {
        Competition competition = new Competition();
        competition.setId((long) i);
        competition.setName("SM i Angered Simhall"+i);
        competition.setDate(LocalDate.now().toString());
        competition.setEndDate(datePlusOneDay());
        competition.setCountry("Sweden");
        competition.setEventType("SM");
        competition.setLocation("Angered Simhall");
        competition.setCity("Göteborg");
        competition.setResults(Collections.emptyList());
        competition.setUsers(Collections.emptyList());
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

    public List<Result> createXResults(int x, final RegisteredUser registeredUser) {
        return IntStream.range(0, x)
                .mapToObj(i -> createResult(i+1, registeredUser))
                .collect(Collectors.toList());
    }

    public Result createResult(int x, RegisteredUser registeredUser) {
        Result result = new Result();
        result.setId((long) x);
        result.setDiscipline(Discipline.STA);
        result.setAnnouncedPerformance(String.valueOf(randomTimeAndPoints()));
        result.setCard(card(x));
        result.setPoints(randomTimeAndPoints());
        result.setUser(registeredUser);
        result.setReportedPerformance(String.valueOf(randomTimeAndPoints()));
        return result;
    }

    public Result createCustomResult(long id, Discipline discipline, Card card, String ap, String rp, double points, String date) {
        Result result = new Result();
        result.setId(id);
        result.setDiscipline(discipline);
        result.setAnnouncedPerformance(ap);
        result.setCard(card);
        result.setPoints(points);
        result.setReportedPerformance(rp);
        result.setDate(date);
        return result;
    }

    public Competition createCustomCompetition(long id, String city, String country, String date, String endDate, String eventType, String location, String name, List<RegisteredUser> registeredUsers, List<Result> results) {
        Competition competition = new Competition();
        competition.setId(id);
        competition.setCity(city);
        competition.setCountry(country);
        competition.setDate(date);
        competition.setEndDate(endDate);
        competition.setEventType(eventType);
        competition.setLocation(location);
        competition.setName(name);
        competition.setUsers(registeredUsers);
        competition.setResults(results);
        return competition;
    }

    private double randomTimeAndPoints() {
        Random random = new Random();
        return random.nextInt(100)+1;
    }

    private Card card(int i) {
        if(i % 3==0)
            return Card.RED;
        else if(i % 3 == 1) {
            return Card.YELLOW;
        }
        else {
            return Card.WHITE;
        }
    }

    public Qualifier createQualifier() {
        Qualifier qualifier = new Qualifier();
        qualifier.setId(1L);
        qualifier.setName("qualifier");
        qualifier.setValueToQualify("4:00.0");
        qualifier.setDiscipline(Discipline.STA);
        qualifier.setStartDate("2020-03-01");
        qualifier.setEndDate("2021-01-03");
        return qualifier;
    }

    public UserDto createUserDto(String email, String firstName, String lastName, String password, String gender) {
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setPassword(password);
        userDto.setGender(gender);
        return userDto;
    }
}
