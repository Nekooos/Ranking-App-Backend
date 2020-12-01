package se.ranking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ranking.model.CompetitionResultDto;
import se.ranking.model.Result;
import se.ranking.service.ResultService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/result")
public class ResultController {
    @Autowired
    private ResultService resultService;

    @GetMapping("/competition-results/{id}")
    public ResponseEntity<?> getCompetitionResultsById(@PathVariable("id") Long id) {
        List<CompetitionResultDto> results = resultService.getCompetitionResultsById(id);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveResult(@RequestBody Result result) {
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
}
