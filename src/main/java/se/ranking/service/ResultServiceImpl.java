package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ranking.exception.EntityNotFoundException;
import se.ranking.model.Competition;
import se.ranking.model.CompetitionResultDto;
import se.ranking.model.Result;
import se.ranking.model.User;
import se.ranking.repository.CompetitionRepository;
import se.ranking.repository.ResultRepository;
import se.ranking.repository.UserRepository;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private UserService userService;

    @Override
    public Result saveResultWithCompetitionAndUser(Result result, Long userId, Long competitionId) {
        User user = userService.findById(userId);
        Competition competition = competitionService.findById(competitionId);
        result.setUser(user);
        result.setCompetition(competition);
        competitionService.editIfUserDoesNotExists(user, competition);
        return resultRepository.save(result);
    }

    @Override
    public Result findById(Long id) {
        return resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result was not found"));
    }

    @Override
    public Result save(Result result) {
        return resultRepository.save(result);
    }

    @Override
    public List<Result> findAll() {
        return resultRepository.findAll();
    }

    @Override
    public Result edit(Long id, Result result) {
        Result targetResult = this.findById(id);
        BeanUtils.copyProperties(result, targetResult, String.valueOf(id));
        return result;
    }

    @Override
    public Result delete(Result result) {
        resultRepository.delete(result);
        return result;
    }

    @Override
    public List<CompetitionResultDto> getCompetitionResultsById(Long id) {
        return resultRepository.getUserAndResultByCompetitionId(id);
    }

    @Override
    public Result patchResult(JsonPatch jsonPatch, Long id) throws JsonPatchException, JsonProcessingException {
        Result result = this.findById(id);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(result, JsonNode.class));
        Result patchedResult = objectMapper.treeToValue(patched, Result.class);

        return resultRepository.save(patchedResult);
    }
}
