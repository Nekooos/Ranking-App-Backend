package se.ranking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.ranking.model.Competition;
import se.ranking.model.User;
import se.ranking.model.UserDto;
import se.ranking.service.UserService;
import se.ranking.util.TestUtil;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserService userService;
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
    @DisplayName("POST user/save")
    public void saveValidUser() throws Exception {
        UserDto userDto = testUtil.createUserDto("valid@mail.com", "John", "Doe", "password", "male");

        Mockito.when(userService.save(userDto))
                .thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(post("/user/save")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("POST user/save throws MethodArgumentNotValidException")
    public void saveNotValidUser() throws Exception {
        UserDto userDto = testUtil.createUserDto("notValidMail.com", "null", "Doe", "password", "male");

        Mockito.when(userService.save(userDto))
                .thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(post("/user/save")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }
}
