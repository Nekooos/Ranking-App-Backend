package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ranking.exception.NotFoundException;
import se.ranking.model.Qualifier;
import se.ranking.model.User;
import se.ranking.repository.QualifierRepository;

import java.util.List;

@Service
public class QualifierServiceImpl implements QualifierService {
    @Autowired
    QualifierRepository qualifierRepository;

    @Autowired
    UtilService<Qualifier> utilService;

    @Override
    public Qualifier findById(Long id) throws NotFoundException{
        return qualifierRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Qualifier save(Qualifier qualifier) {
        return qualifierRepository.save(qualifier);
    }

    @Override
    public List<Qualifier> findAll() {
        return qualifierRepository.findAll();
    }

    @Override
    public Qualifier edit(Long id, Qualifier qualifier) {
        Qualifier targetQualifier = qualifierRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        BeanUtils.copyProperties(qualifier, targetQualifier, String.valueOf(id));
        qualifierRepository.save(targetQualifier);
        return qualifier;
    }

    @Override
    public Qualifier delete(Qualifier qualifier) {
        qualifierRepository.delete(qualifier);
        return qualifier;
    }

    @Override
    public Qualifier patchQualifier(JsonPatch jsonPatch, Long id) throws JsonPatchException, JsonProcessingException {
        Qualifier qualifier = qualifierRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(qualifier, JsonNode.class));
        Qualifier patchedQualifier = objectMapper.treeToValue(patched, Qualifier.class);

        return qualifierRepository.save(patchedQualifier);
    }
}
