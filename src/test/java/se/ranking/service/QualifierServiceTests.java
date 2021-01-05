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
import se.ranking.model.*;
import se.ranking.repository.QualifierRepository;
import se.ranking.repository.UserRepository;
import se.ranking.util.TestUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

public class QualifierServiceTests {
    @InjectMocks
    private QualifierServiceImpl qualifierService;
    @Mock
    private QualifierRepository qualifierRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UtilServiceImpl utilService;

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

        assertEquals("patchedName", result.getName());
    }

    @Test
    public void getQualifiedAndNotQualified() {
        Qualifier qualifier = testUtil.createQualifier();

        when(userRepository.findAll())
                .thenAnswer(i -> createTestUsers());

        when(utilService.convertStringToSeconds(anyString()))
                .thenCallRealMethod();

        List<Set<User>> users = qualifierService.getQualifiedAndNotQualified(qualifier);

        assertEquals(2, users.get(0).size());
        assertEquals(2, users.get(1).size());
    }

    private List<User> createTestUsers() {
        Result result1 = testUtil.createCustomResult(1L, Discipline.STA, Card.WHITE, "3:45.4", "4:21.7", 55.4);
        Result result2 = testUtil.createCustomResult(2L, Discipline.STA, Card.RED, "3:45.4", "4:55.7", 64.4);
        Result result3 = testUtil.createCustomResult(3L, Discipline.FEN, Card.WHITE, "3:45.4", "4:21.7", 55.4);
        Result result4 = testUtil.createCustomResult(4L, Discipline.STA, Card.RED, "3:45.4", "2:55.7", 28.4);
        Result result5 = testUtil.createCustomResult(5L, Discipline.STA, Card.YELLOW, "3:45.4", "2:55.7", 78.4);
        Result result6 = testUtil.createCustomResult(6L, Discipline.STA, Card.YELLOW, "5:45.4", "3:55.7", 18.4);
        Result result7 = testUtil.createCustomResult(7L, Discipline.FEN, Card.RED, ":45.4", "6:55.7", 78.4);
        Result result8 = testUtil.createCustomResult(8L, Discipline.STA, Card.WHITE, "5:45.4", "5:55.7", 18.4);

        User user1 = testUtil.createUser(1, Arrays.asList(result1, result2));
        User user2 = testUtil.createUser(2, Arrays.asList(result3, result4));
        User user3 = testUtil.createUser(3, Arrays.asList(result5, result6));
        User user4 = testUtil.createUser(4, Arrays.asList(result8, result7));

        result1.setUserId(1L);
        result2.setUserId(1L);
        result3.setUserId(2L);
        result4.setUserId(2L);
        result5.setUserId(3L);
        result6.setUserId(3L);
        result7.setUserId(4L);
        result8.setUserId(4L);

        return Arrays.asList(user1, user2, user3, user4);
    }
}
