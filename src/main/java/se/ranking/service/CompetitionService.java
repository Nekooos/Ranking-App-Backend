package se.ranking.service;

import se.ranking.model.Competition;
import se.ranking.model.Qualifier;

import java.util.List;

public interface CompetitionService {
    Competition findById(Long id) throws Exception;
    Competition save(Competition competition);
    List<Competition> findAll();
    Competition edit(Long id, Competition competition) throws Exception;
    Competition delete(Competition competition);
}
