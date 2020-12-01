package se.ranking.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import se.ranking.model.Qualifier;

import java.util.List;

public interface QualifierService {
    Qualifier findById(Long id) throws ChangeSetPersister.NotFoundException;
    Qualifier save(Qualifier qualifier);
    List<Qualifier> findAll();
    Qualifier edit(Long id, Qualifier qualifier);
    Qualifier delete(Qualifier qualifier);

}
