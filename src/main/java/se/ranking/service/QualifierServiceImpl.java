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
import se.ranking.model.Result;
import se.ranking.model.User;
import se.ranking.repository.QualifierRepository;
import se.ranking.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QualifierServiceImpl implements QualifierService {
    @Autowired
    QualifierRepository qualifierRepository;

    @Autowired
    UserRepository userRepository;

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
    public List<List<User>> getQualifiedAndNotQualified(String value, String discipline) {
        List<User> users = userRepository.findAll();
        List<User> qualified = getQualified(value, discipline, users);
        List<User> notQualified = getNotQualified(value, discipline, users);
        List<List<User>> userLists = new ArrayList<>();
        userLists.add(qualified);
        userLists.add(notQualified);
        return userLists;
    }

    private List<User> getQualified(String value, String discipline, List<User> users) {
        List<User> qualified = new ArrayList<>();
        switch (discipline) {
            case "STA":
                double seconds = utilService.convertStringToSeconds(value);
                qualified = filterQualifiedSta(users, seconds);
                break;
            case "FEN":
                double meters = Double.parseDouble(value);
                qualified = filterQualifiedFen(users, meters);
                break;
        }
        return qualified;
    }

    private List<User> getNotQualified(String value, String discipline, List<User> users) {
        List<User> notQualified = new ArrayList<>();
        switch (discipline) {
            case "STA":
                double seconds = utilService.convertStringToSeconds(value);
                notQualified = filterQualifiedSta(users, seconds);
                break;
            case "FEN":
                double meters = Double.parseDouble(value);
                notQualified = filterQualifiedFen(users, meters);
                break;
        }
        return notQualified;
    }

    private List<User> filterQualifiedSta(List<User> users, final double value) {
        List<Result> results = users.stream()
                .flatMap(user -> user.getResults()
                    .stream()
                        .filter(result -> utilService.convertStringToSeconds(result.getReportedPerformance()) > value))
                .collect(Collectors.toList());

        return users.stream()
                .filter(user -> results.stream()
                                .anyMatch(result -> result.getId().equals(user.getId())))
                .collect(Collectors.toList());
    }

    private List<User> filterQualifiedFen(List<User> users, final double value) {
        return null;
    }
}
