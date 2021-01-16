package se.ranking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ranking.model.Record;
import se.ranking.service.RecordService;

import javax.validation.Valid;
import java.util.List;

@RestController("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllRecords() {
        List<Record> records = recordService.getAll();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/get-by-discipline")
    public ResponseEntity<?> getRecordByDiscipline(@PathVariable("discipline") String discipline) {
        Record record = recordService.getByDiscipline(discipline);
        return ResponseEntity.ok(record);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveRecord(@Valid @RequestBody Record record) {
        Record savedRecord = recordService.save(record);
        return ResponseEntity.ok(savedRecord);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editRecord(@Valid @RequestBody Record record, @PathVariable("id") Long id) {
        Record editedRecord = recordService.editRecord(record, id);
        return ResponseEntity.ok(editedRecord);
    }

}
