package se.ranking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ranking.model.Qualifier;
import se.ranking.model.QualifierAnswer;
import se.ranking.model.RegisteredUser;
import se.ranking.service.QualifierAnswerService;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/qualifier-answer")
public class QualifierAnswerController {
    @Autowired
    private QualifierAnswerService qualifierAnswerService;

    @PostMapping("/answer")
    public ResponseEntity<?> saveQualifierAnswer(@RequestBody RegisteredUser registeredUser, @RequestBody Qualifier qualifier, @RequestBody boolean answer) {
        QualifierAnswer qualifierAnswer = qualifierAnswerService.saveQualifierAnswer(registeredUser, qualifier, answer);
        return ResponseEntity.ok(qualifierAnswer);
    }

    /**
     * "Content-Type: application/json-patch+json"
     */
    @PatchMapping(value = "/patch/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> patchQualifierAnswer(@RequestBody JsonPatch jsonPatch, @PathVariable("id") Long id) throws JsonPatchException, JsonProcessingException {
        QualifierAnswer qualifierAnswer = qualifierAnswerService.patchQualifierAnswer(jsonPatch, id);
        return ResponseEntity.ok().body(qualifierAnswer);
    }
}
