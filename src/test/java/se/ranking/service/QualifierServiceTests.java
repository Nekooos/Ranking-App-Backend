package se.ranking.service;

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
import se.ranking.model.Qualifier;
import se.ranking.model.Result;
import se.ranking.model.User;
import se.ranking.repository.QualifierRepository;
import se.ranking.repository.UserRepository;
import se.ranking.util.TestUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class QualifierServiceTests {
    @InjectMocks
    private QualifierServiceImpl qualifierService;
    @Mock
    private QualifierRepository qualifierRepository;
    @Mock
    private UserRepository userRepository;
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

        Assertions.assertEquals("qualifier", qualifier.getName());

        when(qualifierRepository.findById(1L)).thenAnswer(arguments -> Optional.of(qualifier));
        when(qualifierRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        String json = "[{\"op\": \"replace\", \"path\": \"/name\", \"value\": \"patchedName\"}]";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonPatch jsonPatch = JsonPatch.fromJson(jsonNode);

        Qualifier result = qualifierService.patchQualifier(jsonPatch, 1L);

        Assertions.assertEquals("patchedName", result.getName());
    }

    @Test
    public void getQualifiedAndNotQualified() {
        when(userRepository.findAll())
                .thenAnswer(i -> i.getMock())
    }
}
