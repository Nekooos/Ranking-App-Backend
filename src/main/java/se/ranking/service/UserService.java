package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import se.ranking.exception.EntityNotFoundException;
import se.ranking.model.NotRegisteredUser;
import se.ranking.model.User;
import se.ranking.model.UserDto;
import se.ranking.model.UserResultsDto;

import java.util.List;

public interface UserService {
    User findById(Long id) throws EntityNotFoundException;
    User save(UserDto user);
    List<User> findAll();
    User edit(Long id, User user) throws Exception;
    User delete(User user);
    User patchUser(JsonPatch jsonPatch, Long id) throws JsonPatchException, JsonProcessingException;
    List<UserResultsDto> getUserResults(Long id);
    NotRegisteredUser saveNotRegisteredUser(NotRegisteredUser user);
}
