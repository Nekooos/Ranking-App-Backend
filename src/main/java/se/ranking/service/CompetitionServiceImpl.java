package se.ranking.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ranking.model.Competition;
import se.ranking.repository.CompetitionRepository;

import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Override
    public Competition findById(Long id) throws Exception {
        return competitionRepository.findById(id).orElseThrow(()-> new Exception());
    }

    @Override
    public Competition save(Competition competition) {
        return competitionRepository.save(competition);
    }

    @Override
    public List<Competition> findAll() {
        return competitionRepository.findAll();
    }

    @Override
    public Competition edit(Long id, Competition competition) throws Exception {
        Competition targetCompetition = this.findById(id);
        BeanUtils.copyProperties(competition, targetCompetition, String.valueOf(id));
        competitionRepository.save(targetCompetition);
        return competition;
    }

    @Override
    public Competition delete(Competition competition) {
        competitionRepository.delete(competition);
        return competition;
    }
}
