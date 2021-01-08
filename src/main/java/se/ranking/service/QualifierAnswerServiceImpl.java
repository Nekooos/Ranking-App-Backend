package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ranking.exception.EntityNotFoundException;
import se.ranking.model.Qualifier;
import se.ranking.model.QualifierAnswer;
import se.ranking.model.User;
import se.ranking.repository.QualifierAnswerRepository;
import se.ranking.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class QualifierAnswerServiceImpl implements QualifierAnswerService {

    @Autowired
    private QualifierAnswerRepository qualifierAnswerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UtilService utilService;

    @Override
    public QualifierAnswer saveQualifierAnswer(User user, Qualifier qualifier, boolean answer) {
        QualifierAnswer qualifierAnswer = new QualifierAnswer();
        qualifierAnswer.setUser(user);
        qualifierAnswer.setQualifier(qualifier);
        qualifierAnswer.setAnswer(answer);
        qualifierAnswer.setDate(LocalDateTime.now());
        return qualifierAnswerRepository.save(qualifierAnswer);
    }

    @Override
    public QualifierAnswer patchQualifierAnswer(JsonPatch jsonPatch, Long id) throws EntityNotFoundException, JsonPatchException, JsonProcessingException {
        QualifierAnswer qualifierAnswer = this.findById(id);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(qualifierAnswer, JsonNode.class));
        QualifierAnswer patchedQualifierAnswer = objectMapper.treeToValue(patched, QualifierAnswer.class);

        return qualifierAnswerRepository.save(patchedQualifierAnswer);
    }

    @Override
    public QualifierAnswer findById(Long id) throws EntityNotFoundException {
        return qualifierAnswerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Qualifier was not found"));
    }
}
