package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import se.ranking.exception.EntityNotFoundException;
import se.ranking.model.Qualifier;
import se.ranking.model.QualifierAnswer;
import se.ranking.model.User;

public interface QualifierAnswerService {
    QualifierAnswer saveQualifierAnswer(User user, Qualifier qualifier, boolean answer);
    QualifierAnswer findById(Long id);
    QualifierAnswer patchQualifierAnswer(JsonPatch jsonPatch, Long id) throws EntityNotFoundException, JsonPatchException, JsonProcessingException;
}
