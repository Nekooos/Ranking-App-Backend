package se.ranking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import se.ranking.controller.UserController;
import se.ranking.model.Competition;
import se.ranking.model.Qualifier;
import se.ranking.model.Result;
import se.ranking.model.User;
import se.ranking.repository.CompetitionRepository;
import se.ranking.repository.QualifierRepository;
import se.ranking.repository.ResultRepository;
import se.ranking.repository.UserRepository;
import se.ranking.service.CompetitionService;
import se.ranking.service.ResultService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
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

	public static User createUser(int i) {
		User user = new User();
		user.setFirstName("firstName"+i);
		user.setLastName("lastName"+i);
		user.setEmail("test"+i+"@mail.com");
		user.setPassword("password");
		user.setGender(i % 2 == 0 ? "Male" : "Female");
		return user;
	}

	public static Competition createEvent(int i, UserRepository userRepository) {
		Competition competition = new Competition();
		competition.setName("competitionName"+i);
		competition.setDate(LocalDate.now().toString());
		competition.setEndDate(LocalDate.now().toString());
		competition.setCountry("Sweden");
		competition.setCity("Göteborg");
		competition.setLocation("Simhall");
		competition.setEventType("SM");
		List<User> users = userRepository.findAll();
		competition.setUsers(users);
		return competition;
	}

	public static Result createResult(int x, UserRepository userRepository, CompetitionRepository competitionRepository) {
		Result result = new Result();
		result.setDiscipline(x % 2 == 0 ? "STA" : "FEN");
		result.setAnnouncedPerformance(String.valueOf(randomTimeAndPoints()));
		result.setCard(card(x));
		result.setPoints(randomTimeAndPoints());
		result.setUser(userRepository.findById((long) x+1).get());
		result.setCompetition(competitionRepository.findById(1L).get());
		result.setReportedPerformance(String.valueOf(randomTimeAndPoints()));
		result.setRemarks("remark");
		result.setDay(x % 2 == 0 ? 1 : 2);
		return result;
	}

	public static void updateResultWithUSer(Long id, ResultRepository resultRepository, UserRepository userRepository) {
		List<Result> result = resultRepository.findAll();
		List<User> users = userRepository.findAll();
		users.forEach(user -> user.setResults(Arrays.asList()));
	}

	public static void createQualifier(UserRepository userRepository, QualifierRepository qualifierRepository) {
		Qualifier qualifier = new Qualifier();
		qualifier.setName("qualifier1");
		qualifier.setValueToQualify(5.05);
		qualifier.setId(1L);
		qualifier.setUsers(Collections.emptyList());
		qualifierRepository.save(qualifier);
	}

	private static double randomTimeAndPoints() {
		Random random = new Random();
		return random.nextInt(100)+1;
	}

	private static String card(int i) {
		if(i % 3==0)
			return "red";
		else if(i % 3 == 1) {
			return "yellow";
		}
		else {
			return "white";
		}
	}

	private static String discipline(int i) {
		if(i % 3==0)
			return "STA";
		else if(i % 3 == 1) {
			return "FENA";
		}
		else {
			return "UTAN";
		}
	}

}
