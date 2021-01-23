package se.ranking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.ranking.model.Card;
import se.ranking.model.Discipline;
import se.ranking.model.NotRegisteredUserDto;
import se.ranking.model.Result;
import se.ranking.service.ResultService;
import se.ranking.service.UserService;
import se.ranking.service.UserServiceImpl;
import se.ranking.util.TestUtil;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserServiceImpl userService;
    @Autowired
    private MockMvc mockMvc;

    private TestUtil testUtil;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        testUtil = new TestUtil();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("POST user/save-not-registered throws MethodArgumentNotValidException")
    public void saveNotValidUser() throws Exception {
        NotRegisteredUserDto user = testUtil.createNotRegisteredUser(1L, null, "lastName", "Male");

        when(userService.saveNotRegisteredUserDto(user))
                .thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(post("/user/save-not-registered")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message").value("Form validation failed"))
                .andExpect(jsonPath("$.fieldErrors").isArray())
                .andExpect(jsonPath("$.fieldErrors[?(@.field == \"firstName\")]").exists())
                .andExpect(jsonPath("$.fieldErrors[?(@.errorMessage == \"First name is required\")]").exists())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("POST user/save-not-registered")
    public void saveValidResult() throws Exception {
        NotRegisteredUserDto user = testUtil.createNotRegisteredUser(1L, "firstName", "lastName", "Male");

        when(userService.save(any()))
                .thenAnswer(i -> user);

        mockMvc.perform(post("/user/save-not-registered")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }
}
