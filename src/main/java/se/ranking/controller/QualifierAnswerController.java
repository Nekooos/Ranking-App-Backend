package se.ranking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ranking.exception.NotFoundException;
import se.ranking.model.Qualifier;
import se.ranking.model.QualifierAnswer;
import se.ranking.model.User;
import se.ranking.service.QualifierAnswerService;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/qualifier-answer")
public class QualifierAnswerController {
    @Autowired
    private QualifierAnswerService qualifierAnswerService;

    @PostMapping("/answer")
    public ResponseEntity<?> saveQualifierAnswer(@RequestBody User user, @RequestBody Qualifier qualifier, @RequestBody boolean answer) {
        try {
            QualifierAnswer qualifierAnswer = qualifierAnswerService.saveQualifierAnswer(user, qualifier, answer);
            return ResponseEntity.ok(qualifierAnswer);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * "Content-Type: application/json-patch+json"
     */
    @PatchMapping(value = "/patch/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> patchQualifierAnswer(@RequestBody JsonPatch jsonPatch, @PathVariable("id") Long id) {
        try {
            QualifierAnswer qualifierAnswer = qualifierAnswerService.patchQualifierAnswer(jsonPatch, id);
            return ResponseEntity.ok().body(qualifierAnswer);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
