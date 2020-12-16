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
import se.ranking.model.Competition;
import se.ranking.model.Qualifier;
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

    @Override
    public Competition patchCompetition(JsonPatch jsonPatch, Long id) throws JsonPatchException, JsonProcessingException {
        Competition competition = competitionRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(competition, JsonNode.class));
        Competition patchedCompetition = objectMapper.treeToValue(patched, Competition.class);

        return competitionRepository.save(patchedCompetition);
    }
}
