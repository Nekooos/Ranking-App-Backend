package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import se.ranking.exception.EntityNotFoundException;
import se.ranking.model.Competition;
import se.ranking.model.User;

import java.util.List;

public interface CompetitionService {
    Competition findById(Long id) throws EntityNotFoundException;
    Competition save(Competition competition);
    List<Competition> findAll();
    Competition edit(Long id, Competition competition) throws Exception;
    void delete(Long id);
    Competition patchCompetition(JsonPatch jsonPatch, Long id) throws JsonPatchException, JsonProcessingException;
    Competition editIfUserDoesNotExists(User user, Competition competition);
}
