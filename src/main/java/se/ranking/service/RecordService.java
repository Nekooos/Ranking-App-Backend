package se.ranking.service;

import se.ranking.model.Discipline;
import se.ranking.model.Record;

import java.util.List;

public interface RecordService {
    List<Record> getAll();
    Record save(Record record);
    Record getByDiscipline(String discipline);
    Record editRecord(Record record, Long id);
}
