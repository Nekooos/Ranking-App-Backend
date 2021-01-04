package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ranking.exception.NotFoundException;
import se.ranking.model.Qualifier;
import se.ranking.model.QualifierAnswer;
import se.ranking.model.Result;
import se.ranking.model.User;
import se.ranking.repository.QualifierAnswerRepository;
import se.ranking.repository.QualifierRepository;
import se.ranking.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QualifierServiceImpl implements QualifierService {
    @Autowired
    QualifierRepository qualifierRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QualifierAnswerRepository qualifierAnswerRepository;

    @Autowired
    UtilService utilService;

    @Override
    public Qualifier findById(Long id) throws NotFoundException{
        return qualifierRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Qualifier save(Qualifier qualifier) {
        return qualifierRepository.save(qualifier);
    }

    @Override
    public List<Qualifier> findAll() {
        return qualifierRepository.findAll();
    }

    @Override
    public Qualifier edit(Long id, Qualifier qualifier) {
        Qualifier targetQualifier = qualifierRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        BeanUtils.copyProperties(qualifier, targetQualifier, String.valueOf(id));
        qualifierRepository.save(targetQualifier);
        return qualifier;
    }

    @Override
    public Qualifier delete(Qualifier qualifier) {
        qualifierRepository.delete(qualifier);
        return qualifier;
    }

    @Override
    public Qualifier patchQualifier(JsonPatch jsonPatch, Long id) throws JsonPatchException, JsonProcessingException {
        Qualifier qualifier = qualifierRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(qualifier, JsonNode.class));
        Qualifier patchedQualifier = objectMapper.treeToValue(patched, Qualifier.class);

        return qualifierRepository.save(patchedQualifier);
    }

    @Override
    public List<Set<User>> getQualifiedAndNotQualified(String value, String discipline) {
        List<User> users = userRepository.findAll();
        Set<User> qualified = new HashSet<>();
        Set<User> notQualified = new HashSet<>();

        Function<Result, Double> convertTimeToDouble = result ->  utilService.convertStringToSeconds(result.getReportedPerformance());
        Function<Result, Double> convertMetersToDouble = result -> Double.parseDouble(result.getReportedPerformance());

        switch (discipline) {
            case "STA":
                double secondsToQualify = utilService.convertStringToSeconds(value);
                qualified = getQualified(users, secondsToQualify, convertTimeToDouble, discipline);
                notQualified = getNotQualified(qualified, users);
                break;
            case "FEN":
                double metersToQualify = Double.parseDouble(value);
                qualified = getQualified(users, metersToQualify, convertMetersToDouble, discipline);
                notQualified = getNotQualified(qualified, users);
                break;
            //more cases
        }
        return Arrays.asList(qualified, notQualified);
    }

    @Override
    public QualifierAnswer saveQualifierAnswer(User user, Qualifier qualifier, boolean answer) {
        QualifierAnswer qualifierAnswer = new QualifierAnswer();
        qualifierAnswer.setUser(user);
        qualifierAnswer.setQualifier(qualifier);
        qualifierAnswer.setAnswer(answer);
        qualifierAnswer.setDate(LocalDateTime.now());
        return qualifierAnswerRepository.save(qualifierAnswer);
    }

    private Set<User> getQualified(List<User> users, final double value, Function<Result, Double> convertToDouble, String discipline) {
        List<Result> results = users.stream()
                .flatMap(user -> user.getResults()
                        .stream()
                        .filter(result -> convertToDouble.apply(result) >= value && discipline.equals(result.getDiscipline()) && !result.getCard().equals("red")))
                .collect(Collectors.toList());

        return users.stream()
                .filter(user -> results.stream()
                        .anyMatch(result -> result.getUserId().equals(user.getId())))
                .collect(Collectors.toSet());
    }

    private Set<User> getNotQualified(Set<User> qualifiedUsers, List<User> allUsers) {
        return allUsers.stream()
                .filter(user -> !qualifiedUsers.contains(user))
                .collect(Collectors.toSet());
    }
}
