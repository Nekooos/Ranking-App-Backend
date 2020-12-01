package se.ranking.service;

import se.ranking.model.CompetitionResultDto;
import se.ranking.model.Qualifier;
import se.ranking.model.Result;

import java.util.List;

public interface ResultService {
    Result findById(Long id);
    Result save(Result result);
    List<Result> findAll();
    Result edit(Long id, Result result);
    Result delete(Result result);
    List<CompetitionResultDto> getCompetitionResultsById(Long id);
}
