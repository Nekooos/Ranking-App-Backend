package se.ranking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ranking.exception.EntityNotFoundException;
import se.ranking.model.CompetitionResultDto;
import se.ranking.model.Result;
import se.ranking.service.ResultService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/result")
public class ResultController {
    @Autowired
    private ResultService resultService;

    @PostMapping("/save-competition-result")
    public ResponseEntity<?> saveResultWithCompetitionAndUser(@Valid @RequestBody Result result, @PathVariable("userId") Long userId, @PathVariable("competitionId") Long competitionId) {
        Result savedResult = resultService.saveResultWithCompetitionAndUser(result, userId, competitionId);
        return ResponseEntity.ok(savedResult);
    }

    @GetMapping("/competition-results/{id}")
    public ResponseEntity<?> getCompetitionResultsById(@PathVariable("id") Long id) {
        List<CompetitionResultDto> results = resultService.getCompetitionResultsById(id);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveResult(@Valid @RequestBody Result result) {
        Result savedResult = resultService.save(result);
        return ResponseEntity.ok(savedResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResult(@PathVariable("id") Long id) throws Exception {
        Result result = resultService.findById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllResults() {
        List<Result> results = resultService.findAll();
        return ResponseEntity.ok(results);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> editResult(@RequestBody Result result, @PathVariable("id") Long id) {
        Result editedResult = resultService.edit(id, result);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteResult(@PathVariable("id") Long id) {
        Result result = resultService.findById(id);
        resultService.delete(result);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping(value = "/patch/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> patchResult(@RequestBody JsonPatch jsonPatch, @PathVariable("id") Long id) throws JsonPatchException, JsonProcessingException {
        Result result = resultService.patchResult(jsonPatch, id);
        return ResponseEntity.ok().body(result);

    }
}
