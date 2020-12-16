package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import se.ranking.model.Competition;

import java.util.List;

public interface CompetitionService {
    Competition findById(Long id) throws Exception;
    Competition save(Competition competition);
    List<Competition> findAll();
    Competition edit(Long id, Competition competition) throws Exception;
    Competition delete(Competition competition);
    Competition patchCompetition(JsonPatch jsonPatch, Long id) throws JsonPatchException, JsonProcessingException;
}
