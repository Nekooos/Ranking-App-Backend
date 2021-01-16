package se.ranking.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ranking.exception.EntityNotFoundException;
import se.ranking.model.Record;
import se.ranking.repository.RecordRepository;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService{
    @Autowired
    private RecordRepository recordRepository;

    @Override
    public List<Record> getAll() {
        return recordRepository.findAll();
    }

    @Override
    public Record save(Record record) {
        return recordRepository.save(record);
    }

    @Override
    public Record getByDiscipline(String discipline) {
        return recordRepository.findByDiscipline(discipline);
    }

    @Override
    public Record editRecord(Record record, Long id) {
        Record targetRecord = recordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Record was not found"));
        BeanUtils.copyProperties(record, targetRecord, String.valueOf(id));
        return record;
    }


}
