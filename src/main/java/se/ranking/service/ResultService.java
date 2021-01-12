package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import se.ranking.model.CompetitionResultDto;
import se.ranking.model.Result;

import java.util.List;

public interface ResultService {
    Result findById(Long id);
    Result save(Result result);
    List<Result> findAll();
    Result edit(Long id, Result result);
    Result delete(Result result);
    List<CompetitionResultDto> getCompetitionResultsById(Long id);
    Result patchResult(JsonPatch jsonPatch, Long id) throws JsonPatchException, JsonProcessingException;
    Result saveResultWithCompetitionAndUser(Result result, Long userId, Long competitionId);
}
