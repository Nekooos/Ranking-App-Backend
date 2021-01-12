package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.data.crossstore.ChangeSetPersister;
import se.ranking.model.Qualifier;
import se.ranking.model.RegisteredUser;

import java.util.List;
import java.util.Set;

public interface QualifierService {
    Qualifier findById(Long id) throws ChangeSetPersister.NotFoundException;
    Qualifier save(Qualifier qualifier);
    List<Qualifier> findAll();
    Qualifier edit(Long id, Qualifier qualifier);
    Qualifier delete(Qualifier qualifier);
    Qualifier patchQualifier(JsonPatch jsonPatch, Long id) throws JsonPatchException, JsonProcessingException;
    List<Set<RegisteredUser>> getQualifiedAndNotQualified(Qualifier qualifier);
    Qualifier saveWithAllUsers(Qualifier qualifier);
}
