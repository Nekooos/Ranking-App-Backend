package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import se.ranking.model.Qualifier;
import se.ranking.repository.QualifierRepository;
import se.ranking.util.TestUtil;

public class QualifierServiceTests {
    @InjectMocks
    private QualifierServiceImpl qualifierService;
    @Mock
    private QualifierRepository qualifierRepository;
    private TestUtil testUtil;
    private MappingJackson2HttpMessageConverter converter;

    @BeforeEach
    public void setup() {
        qualifierService = new QualifierServiceImpl();
        MockitoAnnotations.initMocks(this);
        testUtil = new TestUtil();
        converter = new MappingJackson2HttpMessageConverter();
    }

    @Test
    public void patchQualifier() throws JsonPatchException, JsonProcessingException {
        Qualifier qualifier = testUtil.createQualifier();
        Mockito.when(qualifierRepository.findById(1L)).thenAnswer(i -> i.getArguments()[0]);
        String json = "{\"op\":\"replace\", \"path\":\"name\",\"value\":\"patchedName\"}";
        JsonPatch jsonPatch = converter.getObjectMapper().convertValue(json, JsonPatch.class);

        Qualifier patchedQualifier = qualifierService.patchQualifier(jsonPatch, 1L);

        Assertions.assertEquals("qualifier", patchedQualifier.getName());
    }
}
