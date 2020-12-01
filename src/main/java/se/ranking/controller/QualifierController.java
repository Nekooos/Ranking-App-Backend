package se.ranking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ranking.model.Qualifier;
import se.ranking.model.Result;
import se.ranking.service.QualifierService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "qualifier")
public class QualifierController {
    @Autowired
    QualifierService qualifierService;

    @PostMapping("/save")
    public ResponseEntity<?> saveQualifier(@RequestBody Qualifier qualifier) {
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

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteQualifier(@RequestBody Qualifier qualifier) {
        qualifierService.delete(qualifier);
        return ResponseEntity.ok().body(qualifier.getName() + " was deleted");
    }
}
