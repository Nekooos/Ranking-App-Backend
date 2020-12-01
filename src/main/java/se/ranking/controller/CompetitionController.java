package se.ranking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ranking.model.Competition;
import se.ranking.service.CompetitionService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/competition")
public class CompetitionController {
    @Autowired
    private CompetitionService competitionService;

    @PostMapping("/save")
    public ResponseEntity<?> saveEvent(@RequestBody Competition competition) {
        Competition savedCompetition = competitionService.save(competition);
        return ResponseEntity.ok(savedCompetition);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") Long id) throws Exception {
        Competition competition = competitionService.findById(id);
        return ResponseEntity.ok(competition);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEvents() {
        List<Competition> competitions = competitionService.findAll();
        return ResponseEntity.ok(competitions);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> editCompetition(@RequestBody Competition competition, @PathVariable("id") Long id) throws Exception {
        Competition editedCompetition = competitionService.edit(id, competition);
        return ResponseEntity.ok().body(editedCompetition);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCompetition(@RequestBody Competition competition) {
        competitionService.delete(competition);
        return ResponseEntity.ok().body(competition.getName() + " was deleted");
    }
}
