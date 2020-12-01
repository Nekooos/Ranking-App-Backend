package se.ranking.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.ranking.exception.NotFoundException;
import se.ranking.model.Qualifier;
import se.ranking.repository.QualifierRepository;

import java.util.List;

@Service
public class QualifierServiceImpl implements QualifierService {
    @Autowired
    QualifierRepository qualifierRepository;

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
        Qualifier targetQualifier = this.findById(id);
        BeanUtils.copyProperties(qualifier, targetQualifier, String.valueOf(id));
        return qualifier;
    }

    @Override
    public Qualifier delete(Qualifier qualifier) {
        qualifierRepository.delete(qualifier);
        return qualifier;
    }
}
