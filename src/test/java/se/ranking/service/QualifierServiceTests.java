package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import se.ranking.model.Qualifier;
import se.ranking.repository.QualifierRepository;
import se.ranking.util.TestUtil;

import java.io.IOException;
import java.util.Optional;

public class QualifierServiceTests {
    @InjectMocks
    private QualifierServiceImpl qualifierService;
    @Mock
    private QualifierRepository qualifierRepository;
    private TestUtil testUtil;

    @BeforeEach
    public void setup() {
        qualifierService = new QualifierServiceImpl();
        MockitoAnnotations.initMocks(this);
        testUtil = new TestUtil();
    }

    @Test
    public void patchQualifier() throws JsonPatchException, IOException {
        Qualifier qualifier = testUtil.createQualifier();

        Mockito.when(qualifierRepository.findById(1L)).thenAnswer(arguments -> Optional.of(qualifier));
        Mockito.when(qualifierRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        String json = "[{\"op\": \"replace\", \"path\": \"/name\", \"value\": \"patchedName\"}]";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonPatch jsonPatch = JsonPatch.fromJson(jsonNode);

        Qualifier result = qualifierService.patchQualifier(jsonPatch, 1L);

        Assertions.assertEquals("patchedName", result.getName());
    }
}
