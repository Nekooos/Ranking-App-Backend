package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ranking.exception.EntityNotFoundException;
import se.ranking.model.*;
import se.ranking.repository.QualifierAnswerRepository;
import se.ranking.repository.QualifierRepository;
import se.ranking.repository.UserRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
       TODO change to queries and more entities for each discipline instead
    */
    @Override
    public List<Set<RegisteredUser>> getQualifiedAndNotQualified(Qualifier qualifier) {
        List<RegisteredUser> registeredUsers = userRepository.findAll();
        Set<RegisteredUser> qualified = new HashSet<>();
        Set<RegisteredUser> notQualified = new HashSet<>();

        Function<String, Double> convertTimeToDouble = string ->  utilService.convertStringToSeconds(string);
        Function<String, Double> convertMetersToDouble = Double::parseDouble;

        switch (qualifier.getDiscipline().getValue()) {
            case "STA":
                qualified = getQualified(registeredUsers, qualifier, convertTimeToDouble);
                notQualified = getNotQualified(qualified, registeredUsers);
                break;
            case "FEN":
                qualified = getQualified(registeredUsers, qualifier, convertMetersToDouble);
                notQualified = getNotQualified(qualified, registeredUsers);
                break;
            //more cases
        }
        return Arrays.asList(qualified, notQualified);
    }


    private Set<RegisteredUser> getQualified(List<RegisteredUser> registeredUsers, Qualifier qualifier, Function<String, Double> convertToDouble) {
        List<Result> results = filterQualifiedResults(registeredUsers, qualifier, convertToDouble);

        return registeredUsers.stream()
                .filter(user -> results.stream()
                        .anyMatch(result -> result.getUserId().equals(user.getId())))
                .collect(Collectors.toSet());
    }

    private List<Result> filterQualifiedResults(List<RegisteredUser> registeredUsers, Qualifier qualifier, Function<String, Double> convertToDouble) {
        return registeredUsers.stream()
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
        LocalDate resultDate = utilService.stringToLocalDate(result.getDate());
        LocalDate qualifierStart = utilService.stringToLocalDate(qualifier.getStartDate());
        LocalDate qualifierEnd = utilService.stringToLocalDate(qualifier.getEndDate());

        return !resultDate.isBefore(qualifierStart) && !resultDate.isAfter(qualifierEnd);
    }

    private Set<RegisteredUser> getNotQualified(Set<RegisteredUser> qualifiedRegisteredUsers, List<RegisteredUser> allRegisteredUsers) {
        return allRegisteredUsers.stream()
                .filter(user -> !qualifiedRegisteredUsers.contains(user))
                .collect(Collectors.toSet());
    }

    @Override
    public Qualifier findById(Long id) throws EntityNotFoundException {
        return qualifierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Qualifier not found"));
    }

    @Override
    public Qualifier save(Qualifier qualifier) {
        return qualifierRepository.save(qualifier);
    }

    @Override
    public Qualifier saveWithAllUsers(Qualifier qualifier) {
        List<Set<RegisteredUser>> userSets = this.getQualifiedAndNotQualified(qualifier);
        userSets.get(0).forEach(user -> qualifierAnswerRepository.save(createQualifierAnswer(user, qualifier, true)));
        userSets.get(1).forEach(user -> qualifierAnswerRepository.save(createQualifierAnswer(user, qualifier, false)));
        return qualifierRepository.save(qualifier);
    }

    private QualifierAnswer createQualifierAnswer(RegisteredUser registeredUser, Qualifier qualifier, boolean isQualified) {
        QualifierAnswer qualifierAnswer = new QualifierAnswer();
        qualifierAnswer.setUser(registeredUser);
        qualifierAnswer.setQualifier(qualifier);
        qualifierAnswer.setQualified(isQualified);
        return qualifierAnswer;
    }

    @Override
    public List<Qualifier> findAll() {
        return qualifierRepository.findAll();
    }

    @Override
    public Qualifier edit(Long id, Qualifier qualifier) {
        Qualifier targetQualifier = this.findById(id);
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
        Qualifier qualifier = this.findById(id);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(qualifier, JsonNode.class));
        Qualifier patchedQualifier = objectMapper.treeToValue(patched, Qualifier.class);

        return qualifierRepository.save(patchedQualifier);
    }
}
