package se.ranking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ranking.exception.EntityNotFoundException;
import se.ranking.model.Qualifier;
import se.ranking.model.User;
import se.ranking.service.QualifierService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "qualifier")
public class QualifierController {
    @Autowired
    QualifierService qualifierService;

    @GetMapping("/qualified/{value}")
    public ResponseEntity<?> getQualifiedAndNotQualified(@RequestBody Qualifier qualifier) {
        List<Set<User>> users = qualifierService.getQualifiedAndNotQualified(qualifier);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveQualifier(@Valid @RequestBody Qualifier qualifier) {
        Qualifier savedQualifier = qualifierService.save(qualifier);
        return ResponseEntity.ok(savedQualifier);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQualifier(@PathVariable("id") Long id) throws Exception {
        Qualifier qualifier = qualifierService.findById(id);
        return ResponseEntity.ok(qualifier);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllQualifiers() {
        List<Qualifier> qualifiers = qualifierService.findAll();
        return ResponseEntity.ok(qualifiers);
    }

    @PutMapping("/put")
    public ResponseEntity<?> editQualifier(@RequestBody Qualifier qualifier, @PathVariable("id") Long id) {
        Qualifier editedQualifier = qualifierService.edit(id, qualifier);
        return ResponseEntity.ok().body(qualifier);
    }

    /**
     * "Content-Type: application/json-patch+json"
     */
    @PatchMapping(value = "/patch/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> patchQualifier(@RequestBody JsonPatch jsonPatch, @PathVariable("id") Long id) throws JsonPatchException, JsonProcessingException {
        Qualifier qualifier = qualifierService.patchQualifier(jsonPatch, id);
        return ResponseEntity.ok().body(qualifier);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQualifier(@RequestBody Qualifier qualifier, @PathVariable("id") Long id) {
        qualifierService.delete(qualifier);
        return ResponseEntity.ok().body(qualifier.getName() + " was deleted");
    }
}
