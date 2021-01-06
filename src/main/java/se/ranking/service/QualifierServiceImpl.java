package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ranking.exception.NotFoundException;
import se.ranking.model.*;
import se.ranking.repository.QualifierAnswerRepository;
import se.ranking.repository.QualifierRepository;
import se.ranking.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    /*
       TODO change to queries and more entities for each discipline
    */
    @Override
    public List<Set<User>> getQualifiedAndNotQualified(Qualifier qualifier) {
        List<User> users = userRepository.findAll();
        Set<User> qualified = new HashSet<>();
        Set<User> notQualified = new HashSet<>();

        Function<String, Double> convertTimeToDouble = string ->  utilService.convertStringToSeconds(string);
        Function<String, Double> convertMetersToDouble = Double::parseDouble;

        switch (qualifier.getDiscipline().getValue()) {
            case "STA":
                qualified = getQualified(users, qualifier, convertTimeToDouble);
                notQualified = getNotQualified(qualified, users);
                break;
            case "FEN":
                qualified = getQualified(users, qualifier, convertMetersToDouble);
                notQualified = getNotQualified(qualified, users);
                break;
            //more cases
        }
        return Arrays.asList(qualified, notQualified);
    }


    private Set<User> getQualified(List<User> users, Qualifier qualifier, Function<String, Double> convertToDouble) {
        List<Result> results = filterQualifiedResults(users, qualifier, convertToDouble);

        return users.stream()
                .filter(user -> results.stream()
                        .anyMatch(result -> result.getUserId().equals(user.getId())))
                .collect(Collectors.toSet());
    }

    private List<Result> filterQualifiedResults(List<User> users, Qualifier qualifier, Function<String, Double> convertToDouble) {
        return users.stream()
                .flatMap(user -> user.getResults()
                        .stream()
                        .filter(result -> convertToDouble.apply(result.getReportedPerformance()) >= convertToDouble.apply(qualifier.getValueToQualify())
                                && qualifier.getDiscipline().equals(result.getDiscipline())
                                && !result.getCard().equals(Card.RED)
                                && resultDateIsWithinQualifierDate(result, qualifier)
                        )
                )
                .collect(Collectors.toList());
    }

    private boolean resultDateIsWithinQualifierDate(Result result, Qualifier qualifier) {
        LocalDate resultDate = utilService.stringToLocalDateTime(result.getDate());
        LocalDate qualifierStart = utilService.stringToLocalDateTime(qualifier.getStartDate());
        LocalDate qualifierEnd = utilService.stringToLocalDateTime(qualifier.getEndDate());

        return !resultDate.isBefore(qualifierStart) && !resultDate.isAfter(qualifierEnd);
    }

    private Set<User> getNotQualified(Set<User> qualifiedUsers, List<User> allUsers) {
        return allUsers.stream()
                .filter(user -> !qualifiedUsers.contains(user))
                .collect(Collectors.toSet());
    }

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
}
