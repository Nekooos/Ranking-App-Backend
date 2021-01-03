package se.ranking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ranking.exception.NotFoundException;
import se.ranking.model.Competition;
import se.ranking.service.CompetitionService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/competition")
public class CompetitionController {
    @Autowired
    private CompetitionService competitionService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveCompetition(@RequestBody Competition competition) {
        try {
            Competition savedCompetition = competitionService.save(competition);
            return ResponseEntity.ok(savedCompetition);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompetition(@PathVariable("id") Long id) throws Exception {
        Competition competition = competitionService.findById(id);
        return ResponseEntity.ok(competition);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCompetitions() {
        List<Competition> competitions = competitionService.findAll();
        return ResponseEntity.ok().body(competitions);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> editCompetition(@RequestBody Competition competition, @PathVariable("id") Long id) throws Exception {
        Competition editedCompetition = competitionService.edit(id, competition);
        return ResponseEntity.ok().body(editedCompetition);
    }

    @PatchMapping(value = "/patch/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> patchCompetition(@RequestBody JsonPatch jsonPatch, @PathVariable("id") Long id) {
        try {
            Competition competition = competitionService.patchCompetition(jsonPatch, id);
            return ResponseEntity.ok().body(competition);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompetition(@PathVariable("id") Long id) {
        try {
            competitionService.delete(id);
            return ResponseEntity.ok().body("Competition was deleted");
        } catch(NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
