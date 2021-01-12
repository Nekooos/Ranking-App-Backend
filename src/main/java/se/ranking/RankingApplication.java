package se.ranking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import se.ranking.controller.UserController;
import se.ranking.model.*;
import se.ranking.repository.CompetitionRepository;
import se.ranking.repository.QualifierRepository;
import se.ranking.repository.ResultRepository;
import se.ranking.repository.UserRepository;
import se.ranking.service.CompetitionService;
import se.ranking.service.ResultService;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootApplication
@ComponentScan
public class RankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RankingApplication.class, args);
	}

	//testdata
	@Bean
	CommandLineRunner init (UserRepository userRepository, CompetitionRepository competitionRepository, ResultRepository resultRepository, CompetitionService competitionService, ResultService resultService, QualifierRepository qualifierRepository, UserController userController){
		return args -> {
			IntStream.range(0, 8)
					.forEach(i -> userRepository.save(createUser(i)));

			IntStream.range(0,8)
					.forEach(i -> competitionRepository.save(createEvent(i, userRepository)));

			IntStream.range(0,8)
					.forEach(i -> resultRepository.save(createResult(i, userRepository, competitionRepository)));

			IntStream.range(0,8)
					.forEach(i -> resultRepository.save(createResult(i, userRepository, competitionRepository)));

			IntStream.range(0,8)
					.forEach(i -> resultRepository.save(createResult(i, userRepository, competitionRepository)));

			//resultRepository.getUserAndResultByCompetitionId(1L).forEach(r -> System.out.println(r.getCard()+"\t"+r.getAnnounced_performance()+"\t"+r.getFirst_name()));
		};

	}

	public static void saveResultToCompetition(CompetitionService competitionService, ResultRepository resultRepository) {
		List<Competition> competitions = competitionService.findAll();
		List<Result> results = resultRepository.findAll();
		competitions.forEach(competition -> {competition.setResults(results);});
		competitions.forEach(competition -> {
			try {
				competitionService.edit(competition.getId(), competition);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public static RegisteredUser createUser(int i) {
		RegisteredUser registeredUser = new RegisteredUser();
		registeredUser.setFirstName("firstName"+i);
		registeredUser.setLastName("lastName"+i);
		registeredUser.setEmail("test"+i+"@mail.com");
		registeredUser.setPassword("password");
		registeredUser.setGender(i % 2 == 0 ? "Male" : "Female");
		return registeredUser;
	}

	public static Competition createEvent(int i, UserRepository userRepository) {
		Competition competition = new Competition();
		competition.setName("competitionName"+i);
		competition.setDate("2021-06-01");
		competition.setEndDate("2021-06-03");
		competition.setCountry("Sweden");
		competition.setCity("Göteborg");
		competition.setLocation("Simhall");
		competition.setEventType("SM");
		List<RegisteredUser> registeredUsers = userRepository.findAll();
		competition.setUsers(registeredUsers);
		return competition;
	}

	public static Result createResult(int x, UserRepository userRepository, CompetitionRepository competitionRepository) {
		Result result = new Result();
		result.setDiscipline(x % 2 == 0 ? Discipline.STA : Discipline.FEN);
		result.setAnnouncedPerformance(String.valueOf(randomTimeAndPoints()));
		result.setCard(card(x));
		result.setPoints(randomTimeAndPoints());
		result.setUser(userRepository.findById((long) x+1).get());
		result.setCompetition(competitionRepository.findById(1L).get());
		result.setReportedPerformance(String.valueOf(randomTimeAndPoints()));
		result.setRemarks("remark");
		result.setDate("2021-06-03");
		return result;
	}

	public static void updateResultWithUSer(Long id, ResultRepository resultRepository, UserRepository userRepository) {
		List<Result> result = resultRepository.findAll();
		List<RegisteredUser> registeredUsers = userRepository.findAll();
		registeredUsers.forEach(user -> user.setResults(Arrays.asList()));
	}

	public static void createQualifier(UserRepository userRepository, QualifierRepository qualifierRepository) {
		Qualifier qualifier = new Qualifier();
		qualifier.setName("qualifier1");
		qualifier.setValueToQualify("5.05");
		qualifier.setId(1L);
		qualifier.setStartDate("2021-01-01");
		qualifier.setEndDate("2021-12-31");
		qualifierRepository.save(qualifier);
	}

	private static double randomTimeAndPoints() {
		Random random = new Random();
		return random.nextInt(100)+1;
	}

	private static Card card(int i) {
		if(i % 3==0)
			return Card.RED;
		else if(i % 3 == 1) {
			return Card.YELLOW;
		}
		else {
			return Card.WHITE;
		}
	}
}
